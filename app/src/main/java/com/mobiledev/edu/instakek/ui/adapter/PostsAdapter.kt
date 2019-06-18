package com.mobiledev.edu.instakek.ui.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.mobiledev.edu.instakek.R
import com.mobiledev.edu.instakek.data.database.entity.Post

class PostsAdapter(private val mClickHandler: PostsAdapterOnClickHandler)
    : RecyclerView.Adapter<PostsAdapter.PostsAdapterViewHolder>() {

    private var mPosts: List<Post>? = null

    interface PostsAdapterOnClickHandler {
        fun onClick(likes: String)
    }

    inner class PostsAdapterViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
        val mLikesTextView: TextView
        val mChannelNameTextView: TextView
        val mDescriptionTextView: TextView

        init {
            mLikesTextView = view.findViewById(R.id.tv_post_likes) as TextView
            mChannelNameTextView = view.findViewById(R.id.username_text) as TextView
            mDescriptionTextView = view.findViewById(R.id.caption_text) as TextView

            view.setOnClickListener(this)
        }

        override fun onClick(v: View) {
            val adapterPosition = adapterPosition
            val likes = mPosts!![adapterPosition]
            mClickHandler.onClick(likes.channel!!.channelName)
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
        val currentPost = mPosts!![position]

        while (currentPost.channel == null) {
            postsAdapterViewHolder.mChannelNameTextView.text = postsAdapterViewHolder.itemView.context.applicationContext.getString(R.string.post_channel_name_loading)
        }
        postsAdapterViewHolder.mChannelNameTextView.text = currentPost.channel!!.channelName

        while (currentPost.likesAmount == null) {
            postsAdapterViewHolder.mLikesTextView.text = postsAdapterViewHolder.itemView.context.applicationContext.getString(R.string.post_likes_loading)
        }
        postsAdapterViewHolder.mLikesTextView.text = postsAdapterViewHolder.itemView.context.applicationContext.getString(R.string.post_likes, currentPost.likesAmount.toString())

        postsAdapterViewHolder.mDescriptionTextView.text = currentPost.text
    }

    override fun getItemCount(): Int {
        return if (null == mPosts) 0 else mPosts!!.size
    }

    fun setPosts(posts: List<Post>) {
        mPosts = posts
        notifyDataSetChanged()
    }
}