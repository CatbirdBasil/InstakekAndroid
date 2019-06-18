package com.mobiledev.edu.instakek.data.repository.impl

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Transformations
import android.content.Context
import android.os.AsyncTask
import android.util.Log
import com.mobiledev.edu.instakek.data.database.AppDatabase
import com.mobiledev.edu.instakek.data.database.dao.*
import com.mobiledev.edu.instakek.data.database.entity.*
import com.mobiledev.edu.instakek.data.network.requestApi.ChannelRequests
import com.mobiledev.edu.instakek.data.network.utils.ApiEndpoints
import com.mobiledev.edu.instakek.data.network.utils.NetworkUtils
import com.mobiledev.edu.instakek.data.repository.ChannelRepository
import com.mobiledev.edu.instakek.data.repository.FetchingRepository
import com.mobiledev.edu.instakek.utils.AuthUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChannelRepositoryImpl(val context: Context) : ChannelRepository, FetchingRepository() {

    companion object {
        private val TAG = ChannelRepositoryImpl::class.qualifiedName
    }

    private val database = AppDatabase.getAppDataBase(context)!!

    private val postDao: PostDao = database.postDao()
    private val postContentDao: PostContentDao = database.postContentDao()
    private val channelDao: ChannelDao = database.channelDao()
    private val subscriptionDao: SubscriptionDao = database.subscriptionDao()
    private val commentDao: CommentDao = database.commentDao()
    private val likesDao: LikesDao = database.likesDao()

    private val channelApi: ChannelRequests = ApiEndpoints.Channel

    private val CURRENT_USER_ID = AuthUtils.CURRENT_USER_ID

    override fun getCurrentUserBaseChannel(): LiveData<Channel> = getBaseChannelByUserId(CURRENT_USER_ID)

    override fun getBaseChannelByUserId(id: Long): LiveData<Channel> {
        Log.d(TAG, "Attempting to fetch base channel of user(id = $id)")

        if (!isRecent && !isFetchingData && NetworkUtils.isOnline(context.applicationContext)) {
            Log.d(TAG, "Attempting to fetch base channel of user(id = $id) from api")

            val channelCallback = channelApi.getBaseChannelByUserId(id)
            isFetchingData = true

            channelCallback.enqueue(object : Callback<Channel> {

                override fun onResponse(call: Call<Channel>?, response: Response<Channel>?) {
                    if (response!!.isSuccessful) {
                        insertRequiredChannelDataAsync(response.body())
                        isRecent = true
                    } else {
                        Log.d(TAG, "Error occurred while fetching base channel. " +
                                "Error code: ${response.code()}")
                    }
                    isFetchingData = false
                }

                override fun onFailure(call: Call<Channel>?, t: Throwable?) {
                    Log.d(TAG, "Error occurred while fetching base channel", t)
                    isFetchingData = false
                }
            })
        }
        val channelLiveData: LiveData<Channel> = channelDao.getBaseChannelByUserId(CURRENT_USER_ID)

        return Transformations.map(channelLiveData, { channel ->
            fetchContentsAndChannelForPosts(channel.posts!!)
            channel
        })
    }

    private fun insertRequiredChannelDataAsync(channel: Channel) {
        AsyncTask.execute {
            database.runInTransaction(Runnable {
                channelDao.insert(channel)
                channel.posts!!.forEach {
                    Log.d(TAG, "Curr post: ${it.id}, ${it.text}")
                    postDao.insert(it)
                    subscriptionDao.insertSubscription(
                            Subscription(it.channelId, CURRENT_USER_ID, true))
                    synchroniseLikesWithRemoteDb(it.id, *it.likes!!.toTypedArray())
                    postContentDao.insertAll(*it.contents!!.toTypedArray())
                }
            })
        }
    }

    private fun synchroniseLikesWithRemoteDb(postId: Long, vararg likedUsers: User) {
        likesDao.clearPostLikes(postId)
        likedUsers.forEach { likesDao.insert(Likes(it.id, postId)) }
    }

    private fun fetchContentsAndChannelForPosts(posts: List<Post>) {
        AsyncTask.execute {
            posts.forEach {
                Log.d(TAG, "Curr DB post: ${it.id}, ${it.text}")
                it.contents = postContentDao.getByPostId(it.id)
                it.channel = channelDao.getChannelByPostId(it.id)
                it.likes = likesDao.getLikedUsersByPostId(it.id)
                it.likesAmount = likesDao.countLikedUsersByPostId(it.id)
                it.isLikedByCurrentUser = likesDao
                        .amountOfLikesFromUserToPost(CURRENT_USER_ID, it.id) > 0
            }
        }
    }

    override fun getAll(): LiveData<List<Channel>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getById(id: Long): LiveData<Channel> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun insert(entity: Channel) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun update(entity: Channel) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun delete(id: Long) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}