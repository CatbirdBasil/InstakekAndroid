package com.mobiledev.edu.instakek.ui.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.mobiledev.edu.instakek.R

class PostsAdapter(private val mClickHandler: PostsAdapterOnClickHandler)
    : RecyclerView.Adapter<PostsAdapter.PostsAdapterViewHolder>() {

    init {
        var array = listOf("1", "2", "3")
        setPosts(array)
    }

    private var mPosts: List<String>? = null

    interface PostsAdapterOnClickHandler {
        fun onClick(likes: String)
    }

    inner class PostsAdapterViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
        val mLikesTextView: TextView

        init {
            mLikesTextView = view.findViewById(R.id.tv_post_likes) as TextView

            view.setOnClickListener(this)
        }

        override fun onClick(v: View) {
            val adapterPosition = adapterPosition
            val likes = mPosts!![adapterPosition]
            mClickHandler.onClick(likes)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): PostsAdapterViewHolder {
        val context = viewGroup.context
        val layoutIdForListItem = R.layout.post_list_item
        val inflater = LayoutInflater.from(context)
        val shouldAttachToParentImmediately = false

        val view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately)

        return PostsAdapterViewHolder(view)
    }

    override fun onBindViewHolder(postsAdapterViewHolder: PostsAdapterViewHolder, position: Int) {
        val postLikes = mPosts!![position]
        postsAdapterViewHolder.mLikesTextView.text = postLikes
    }

    override fun getItemCount(): Int {
        return if (null == mPosts) 0 else mPosts!!.size
    }

    fun setPosts(posts: List<String>) {
        mPosts = posts
        notifyDataSetChanged()
    }
}