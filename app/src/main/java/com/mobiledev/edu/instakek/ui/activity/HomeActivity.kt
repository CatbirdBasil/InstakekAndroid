package com.mobiledev.edu.instakek.ui.activity

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.AsyncTask
import android.os.Bundle
import android.os.SystemClock
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.widget.Toast
import com.facebook.drawee.backends.pipeline.Fresco
import com.mobiledev.edu.instakek.R
import com.mobiledev.edu.instakek.data.database.entity.Post
import com.mobiledev.edu.instakek.data.network.utils.NetworkUtils
import com.mobiledev.edu.instakek.ui.adapter.PostsAdapter
import com.mobiledev.edu.instakek.ui.viewModel.PostViewModel
import com.mobiledev.edu.instakek.utils.ActivityUtils
import com.mobiledev.edu.instakek.utils.extentions.makeInvisible
import com.mobiledev.edu.instakek.utils.extentions.makeVisible
import com.stfalcon.frescoimageviewer.ImageViewer
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : BottomNavigationActivity(0), PostsAdapter.PostsAdapterOnClickHandler,
        PostsAdapter.LikeOnClickHandler , PostsAdapter.ImageOnClickHandler{


    companion object {
        private val TAG = HomeActivity::class.qualifiedName
    }

    private var mRecyclerView: RecyclerView? = null
    private var mPostsAdapter: PostsAdapter? = null
    private var mPostViewModel: PostViewModel? = null
//    private var mUserViewModel: UserViewModel? = null;


//    private var mErrorMessageDisplay: TextView? = null
//    private var mLoadingIndicator: ProgressBar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setupBottomNavigation()

        Fresco.initialize(this)
        mRecyclerView = findViewById(R.id.recyclerview_posts)

//        mErrorMessageDisplay = findViewById(R.id.tv_error_message_display) as TextView

        val layoutManager = LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL,
                false)

        mRecyclerView!!.layoutManager = layoutManager

        mRecyclerView!!.setHasFixedSize(true)

        mPostsAdapter = PostsAdapter(this, this,this)

        mRecyclerView!!.adapter = mPostsAdapter

        mPostViewModel = ViewModelProviders.of(this).get(PostViewModel::class.java)
//        mUserViewModel = ViewModelProviders.of(this).get(UserViewModel::class.java)

        if (mPostViewModel != null) {
            mPostViewModel!!.getSubscribedPosts().observe(this, Observer<List<Post>> { posts ->

                Log.d(TAG, "Posts: " + posts)
                if (posts != null) {
                    mPostsAdapter!!.setPosts(posts)
                }
            })
        }

        if (swipe_refresh_home != null) {
            swipe_refresh_home.setOnRefreshListener {

                if (!NetworkUtils.isOnline(this.applicationContext)) {
                    ActivityUtils.showNoInternetToast(applicationContext)
                    swipe_refresh_home.isRefreshing = false
                } else {
                    Log.d(TAG, "CUSTOM_LOG: Starting to refresh posts...")
                    mPostViewModel!!.invalidateData()

                    AsyncTask.execute {
                        while (mPostViewModel!!.isFetchingData()) {
                            SystemClock.sleep(10)
                        }
                        runOnUiThread { swipe_refresh_home.isRefreshing = false }
                        Log.d(TAG, "Finished fetching posts from api")
                    }
                }
            }
        }

        //                if (mUserViewModel != null) {
//            mUserViewModel!!.getUsers().observe(this, Observer<List<User>> {users ->
//
//                Log.d(TAG, "Users: " + users)
//
//                var list: ArrayList<String> = ArrayList()
//                users!!.forEach { list.add(it.username) }
//                mPostsAdapter!!.setPosts(list)
//            })
//        }

//        mLoadingIndicator = findViewById(R.id.pb_loading_indicator) as ProgressBar

        loadPostsData()
    }

    override fun onResume() {
        super.onResume()

        if (mPostViewModel != null) {
            mPostViewModel!!.invalidateData()
        }

        if (!NetworkUtils.isOnline(this.applicationContext)) {
            ActivityUtils.showNoInternetToast(applicationContext)
        }

//        if (mUserViewModel != null) {
//            mUserViewModel!!.invalidateData()
//        }
    }

    private fun loadPostsData() {
        showPostsDataView()

        // FetchWeatherTask().execute(location)
    }

    override fun onClick(weatherForDay: String) {
        val context = this
        Toast.makeText(context, weatherForDay, Toast.LENGTH_SHORT)
                .show()
    }

    override fun onLikeClick(post: Post) {
        val context = this

        if (post.isLikedByCurrentUser) {
            mPostViewModel!!.likePost(post.id!!)
        } else {
            mPostViewModel!!.dislikePost(post.id!!)
        }
    }

    private fun showPostsDataView() {
        //mErrorMessageDisplay!!.makeInvisible()
        mRecyclerView!!.makeVisible()
    }

    private fun showErrorMessage() {
        mRecyclerView!!.makeInvisible()
        //mErrorMessageDisplay!!.makeInvisible()
    }

    override fun onImageClick(post: Post) {
        ImageViewer.Builder(this, post.contents)
                // .setPlaceholderImage();
                // .setStartPosition(startPosition)
                .allowZooming(true)
                .allowSwipeToDismiss(true)
                .show();
    }
}
