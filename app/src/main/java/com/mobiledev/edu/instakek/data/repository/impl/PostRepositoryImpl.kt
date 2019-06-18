package com.mobiledev.edu.instakek.data.repository.impl

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Transformations
import android.content.Context
import android.os.AsyncTask
import android.util.Log
import com.mobiledev.edu.instakek.data.database.AppDatabase
import com.mobiledev.edu.instakek.data.database.dao.*
import com.mobiledev.edu.instakek.data.database.entity.Likes
import com.mobiledev.edu.instakek.data.database.entity.Post
import com.mobiledev.edu.instakek.data.database.entity.Subscription
import com.mobiledev.edu.instakek.data.database.entity.User
import com.mobiledev.edu.instakek.data.network.requestApi.PostRequests
import com.mobiledev.edu.instakek.data.network.utils.ApiEndpoints
import com.mobiledev.edu.instakek.data.network.utils.NetworkUtils
import com.mobiledev.edu.instakek.data.repository.FetchingRepository
import com.mobiledev.edu.instakek.data.repository.PostRepository
import com.mobiledev.edu.instakek.utils.AuthUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class PostRepositoryImpl(val context: Context) : PostRepository, FetchingRepository() {

    companion object {
        private val TAG = PostRepositoryImpl::class.qualifiedName
    }

    private val database = AppDatabase.getAppDataBase(context)!!

    private val postDao: PostDao = database.postDao()
    private val postContentDao: PostContentDao = database.postContentDao()
    private val channelDao: ChannelDao = database.channelDao()
    private val subscriptionDao: SubscriptionDao = database.subscriptionDao()
    private val commentDao: CommentDao = database.commentDao()
    private val userDao: UserDao = database.userDao()
    private val likesDao: LikesDao = database.likesDao()

    private val postApi: PostRequests = ApiEndpoints.Post

    private val CURRENT_USER_ID = AuthUtils.CURRENT_USER_ID

//    private var isRecent: Boolean = false
//    private var isFetchingData: Boolean = false
//    private var isFetchingDataLiveData: MutableLiveData<Boolean> = MutableLiveData()
//
//    init {
//        isFetchingDataLiveData.value = false
//    }
//
//    override fun invalidateData() {
//        isRecent = false
//    }
//
//    override fun isFetchingData(): LiveData<Boolean> {
//        if (isFetchingDataLiveData.value != isFetchingData) {
//            isFetchingDataLiveData.value = isFetchingData
//        }
//        return isFetchingDataLiveData
//    }

    override fun getAll(): LiveData<List<Post>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getPostsFromSubscribedChannels(): LiveData<List<Post>> {
        Log.d(TAG, "Attempting to fetch post form channels that current user is subscribed to")

        if (!isRecent && !isFetchingData && NetworkUtils.isOnline(context.applicationContext)) {
            Log.d(TAG, "Attempting to fetch post form channels" +
                    " that current user is subscribed to from api")

            val postCallback = postApi.getPostsFromSubscribedChannels()
            isFetchingData = true

            postCallback.enqueue(object : Callback<List<Post>> {

                override fun onResponse(call: Call<List<Post>>?, response: Response<List<Post>>?) {
                    if (response!!.isSuccessful) {

                        insertRequiredPostDataAsync(response.body())
                        isRecent = true
                    } else {
                        Log.d(TAG, "Error occurred while fetching subscribed posts. " +
                                "Error code: ${response.code()}")
                    }
                    isFetchingData = false
                }

                override fun onFailure(call: Call<List<Post>>?, t: Throwable?) {
                    Log.d(TAG, "Error occurred while fetching subscribed posts", t)
                    isFetchingData = false
                }
            })
        }
        val postsLiveData: LiveData<List<Post>> = postDao.getPostsFromSubscribedChannels(CURRENT_USER_ID)

        return Transformations.map(postsLiveData, { regularPosts ->
            fetchContentsAndChannelForPosts(regularPosts)
            regularPosts
        })
    }

    private fun insertRequiredPostDataAsync(posts: List<Post>) {
        AsyncTask.execute {
            database.runInTransaction(Runnable {
                posts.forEach {
                    Log.d(TAG, "Curr post: $it")
                    channelDao.insert(it.channel!!)
                    subscriptionDao.insertSubscription(
                            Subscription(it.channelId, CURRENT_USER_ID, true))
                    insertAllLikes(it.id!!, *it.likes!!.toTypedArray())
                    postDao.insert(it)
                    postContentDao.insertAll(*it.contents!!.toTypedArray())
                }
            })
        }
    }

    //TODO DELETE OLD LIKES
    private fun insertAllLikes(postId: Long, vararg likedUsers: User) {
        likedUsers.forEach { likesDao.insert(Likes(it.id!!, postId)) }
    }

    private fun fetchContentsAndChannelForPosts(posts: List<Post>) {
        AsyncTask.execute {
            posts.forEach {
                it.contents = postContentDao.getByPostId(it.id!!)
                it.channel = channelDao.getChannelByPostId(it.id!!)
                it.likes = likesDao.getLikedUsersByPostId(it.id!!)
                it.likesAmount = likesDao.countLikedUsersByPostId(it.id!!)
            }
        }
    }

    override fun getPostWithComments(postId: Long): LiveData<Post> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getById(id: Long): LiveData<Post> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun insert(entity: Post) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun update(entity: Post) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun delete(id: Long) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}