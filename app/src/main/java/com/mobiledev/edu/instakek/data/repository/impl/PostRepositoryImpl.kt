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
                    Log.d(TAG, "Curr post: ${it.id}, ${it.text}")
                    channelDao.insert(it.channel!!)
//                    channelDao.insert(Channel(it.channelId, it.channel!!.creatorId,
//                            it.channel!!.channelType, it.channel!!.channelName,
//                            it.channel!!.creationDate, it.channel!!.imgSrc))
                    val check = postDao.insert(it)
                    Log.d(TAG, "OUR MAGIC ID: $check")
                    subscriptionDao.insertSubscription(
                            Subscription(it.channelId, CURRENT_USER_ID, true))
                    synchroniseLikesWithRemoteDb(it.id!!, *it.likes!!.toTypedArray())
                    postContentDao.insertAll(*it.contents!!.toTypedArray())
                }
            })
        }
    }

    private fun synchroniseLikesWithRemoteDb(postId: Long, vararg likedUsers: User) {
        likesDao.clearPostLikes(postId)
        likedUsers.forEach { likesDao.insert(Likes(it.id!!, postId)) }
    }

    private fun fetchContentsAndChannelForPosts(posts: List<Post>) {
        Log.d(TAG, "WE CAME TO FETCH METHOD")
        AsyncTask.execute {
            posts.forEach {
                Log.d(TAG, "Curr DB post: ${it.id}, ${it.text}")
                it.contents = postContentDao.getByPostId(it.id)
                Log.d(TAG, "CURR CONTENT: ${it.contents!![0].contentLink}")
                it.channel = channelDao.getChannelByPostId(it.id!!)
                Log.d(TAG, "CURR CHANNEL: ${it.channel!!.channelName}")
                it.likes = likesDao.getLikedUsersByPostId(it.id!!)
                Log.d(TAG, "CURR LIKES: ${it.likes}")
                it.likesAmount = likesDao.countLikedUsersByPostId(it.id!!)
                Log.d(TAG, "LIKES AMOUNT: ${it.likesAmount}")
                it.isLikedByCurrentUser = likesDao
                        .amountOfLikesFromUserToPost(CURRENT_USER_ID, it.id!!) > 0
                Log.d(TAG, "IS LIKED: ${it.isLikedByCurrentUser}")
            }
        }
    }

    override fun likePost(postId: Long) {
        Log.d(TAG, "Attempting to like post(id = $postId) by user(id = $CURRENT_USER_ID")

        val postCallback = postApi.likePost(postId)

        postCallback.enqueue(object : Callback<Void> {

            override fun onResponse(call: Call<Void>?, response: Response<Void>?) {
                if (response!!.isSuccessful) {
                    likePostAsync(postId)
                } else {
                    Log.d(TAG, "Error occurred while liking post " +
                            "Error code: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<Void>?, t: Throwable?) {
                Log.d(TAG, "Error occurred while liking post", t)
            }
        })
    }

    private fun likePostAsync(postId: Long) {
        AsyncTask.execute {
            likesDao.insert(Likes(CURRENT_USER_ID, postId))
        }
    }

    override fun dislikePost(postId: Long) {
        Log.d(TAG, "Attempting to dislike post(id = $postId) by user(id = $CURRENT_USER_ID")

        val postCallback = postApi.dislikePost(postId)

        postCallback.enqueue(object : Callback<Void> {

            override fun onResponse(call: Call<Void>?, response: Response<Void>?) {
                if (response!!.isSuccessful) {
                    dislikePostAsync(postId)
                } else {
                    Log.d(TAG, "Error occurred while disliking post " +
                            "Error code: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<Void>?, t: Throwable?) {
                Log.d(TAG, "Error occurred while disliking post", t)
            }
        })
    }

    private fun dislikePostAsync(postId: Long) {
        AsyncTask.execute {
            likesDao.delete(Likes(CURRENT_USER_ID, postId))
        }
    }

//    private fun insertPost(post:Post){
//        AsyncTask.execute {
//            database.runInTransaction(Runnable {
//                    var channel = channelDao.getById(AuthUtils.CURRENT_USER_ID)
//                    var Id = channel.id
//                    Log.d(TAG, "Curr post: ${post.text}")
////                    Channel(it.channelId, it.channel!!.)
//                    channelDao.insert(it.channel!!)
//                    postDao.insert(it)
//                    subscriptionDao.insertSubscription(
//                            Subscription(it.channelId, CURRENT_USER_ID, true))
//                    synchroniseLikesWithRemoteDb(it.id!!, *it.likes!!.toTypedArray())
//                    postContentDao.insertAll(*it.contents!!.toTypedArray())
//
//            })
//        }
//
//    }

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