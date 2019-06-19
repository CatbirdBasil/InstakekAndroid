package com.mobiledev.edu.instakek.ui.activity

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.os.SystemClock
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.widget.Button
import android.widget.Toast
import com.mobiledev.edu.instakek.R
import com.mobiledev.edu.instakek.data.database.entity.Channel
import com.mobiledev.edu.instakek.data.database.entity.Post
import com.mobiledev.edu.instakek.ui.adapter.PostsAdapter
import com.mobiledev.edu.instakek.ui.viewModel.ChannelViewModel
import com.mobiledev.edu.instakek.ui.viewModel.PostViewModel
import com.stfalcon.frescoimageviewer.ImageViewer

class ProfileActivity : BottomNavigationActivity(4), PostsAdapter.PostsAdapterOnClickHandler,
        PostsAdapter.LikeOnClickHandler, PostsAdapter.ImageOnClickHandler {

    private val TAG = ProfileActivity::class.qualifiedName

    private lateinit var createPost: Button

    private var mRecyclerView: RecyclerView? = null
    private var mPostsAdapter: PostsAdapter? = null
    private var mChannelViewModel: ChannelViewModel? = null
    private var mPostViewModel: PostViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        setupBottomNavigation()
        setButtonValue()
        createPost = findViewById(R.id.create_post)

        mRecyclerView = findViewById(R.id.recyclerview_user_posts)

        val layoutManager = LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL,
                false)

        mRecyclerView!!.layoutManager = layoutManager

        mRecyclerView!!.setHasFixedSize(false)

        mPostsAdapter = PostsAdapter(this, this, this)

        mRecyclerView!!.adapter = mPostsAdapter

        mChannelViewModel = ViewModelProviders.of(this).get(ChannelViewModel::class.java)
        mPostViewModel = ViewModelProviders.of(this).get(PostViewModel::class.java)

        if (mChannelViewModel != null) {
            mChannelViewModel!!.getCurrentUserBaseChannel()
                    .observe(this, Observer<Channel> { channel ->

                        Log.d(TAG, "Channel updating...")

                        if (channel != null) {
                            Log.d(TAG, "Channel: ${channel.id}")
                            AsyncTask.execute {
                                while (channel.posts == null) {
                                    SystemClock.sleep(10)
                                }
                                runOnUiThread { mPostsAdapter!!.setPosts(channel.posts!!) }
                            }

                        }
                    })
        }

        createPost.setOnClickListener { getToCreatePost() }

    }

    private fun getToCreatePost() {
        var intent: Intent = Intent(this, CreatePostActivity::class.java)

        startActivity(intent)
        finish()
    }

    override fun onClick(weatherForDay: String) {
        val context = this
        Toast.makeText(context, weatherForDay, Toast.LENGTH_SHORT)
                .show()
    }

    override fun onLikeClick(post: Post) {
        val context = this

        if (post.isLikedByCurrentUser) {
            mPostViewModel!!.likePost(post.id)
        } else {
            mPostViewModel!!.dislikePost(post.id)
        }
    }

    override fun onImageClick(post: Post) {
        Log.d(TAG, post.contents!![0].contentLink)
        var listimages = arrayOf(post.contents!![0].contentLink);

        ImageViewer.Builder(this, listimages)
                // .setPlaceholderImage();
                // .setStartPosition(startPosition)
                .allowZooming(true)
                .allowSwipeToDismiss(true)
                .show();
    }

    fun setUsername() {

    }

    //TODO delete this
    fun setButtonValue() {


    }
}
