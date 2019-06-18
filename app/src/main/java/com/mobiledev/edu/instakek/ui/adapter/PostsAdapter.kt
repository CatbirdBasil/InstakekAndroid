package com.mobiledev.edu.instakek.ui.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.mobiledev.edu.instakek.R
import com.mobiledev.edu.instakek.data.database.entity.Post
import com.squareup.picasso.Picasso

class PostsAdapter(private val mClickHandler: PostsAdapterOnClickHandler,
                   private val mLikeClickHandler: LikeOnClickHandler,
                   private val mImageClickHandler:ImageOnClickHandler
                   )
    : RecyclerView.Adapter<PostsAdapter.PostsAdapterViewHolder>() {

    private var mPosts: List<Post>? = null

    interface PostsAdapterOnClickHandler {
        fun onClick(likes: String)
    }

    interface LikeOnClickHandler {
        fun onLikeClick(post: Post)
    }


    interface ImageOnClickHandler {
        fun onImageClick(post: Post)
    }


    inner class PostsAdapterViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
        val mLikesTextView: TextView
        val mChannelNameTextView: TextView
        val mDescriptionTextView: TextView
        val mLikeButton: Button
        val imageView:ImageView

        init {
            mLikesTextView = view.findViewById(R.id.tv_post_likes) as TextView
            mChannelNameTextView = view.findViewById(R.id.username_text) as TextView
            mDescriptionTextView = view.findViewById(R.id.caption_text) as TextView
            mLikeButton = view.findViewById(R.id.like_image) as Button
            imageView = view.findViewById(R.id.post_image)

            view.setOnClickListener(this)

            imageView.setOnClickListener{
                val adapterPosition = adapterPosition
                val currentPost = mPosts!![adapterPosition]
                mImageClickHandler.onImageClick(currentPost)
            }


            mLikeButton.setOnClickListener {
                val adapterPosition = adapterPosition
                val currentPost = mPosts!![adapterPosition]
                currentPost.isLikedByCurrentUser = !currentPost.isLikedByCurrentUser
                changeButtonAppearence(this, currentPost)
                mLikeClickHandler.onLikeClick(currentPost)
            }
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
        postsAdapterViewHolder.mLikesTextView.text = postsAdapterViewHolder.itemView.context
                .applicationContext.getString(R.string.post_likes, currentPost.likesAmount.toString())

        postsAdapterViewHolder.mDescriptionTextView.text = currentPost.text

        changeButtonAppearence(postsAdapterViewHolder, currentPost)

        if(currentPost.contents!=null) {
            Picasso.get()
                    .load(currentPost.contents!![0].contentLink)
                    //.fit()
                    .into(postsAdapterViewHolder.imageView)
        }

    }

    override fun getItemCount(): Int {
        return if (null == mPosts) 0 else mPosts!!.size
    }

    fun setPosts(posts: List<Post>) {
        mPosts = posts
        notifyDataSetChanged()
    }

    fun changeButtonAppearence(postsAdapterViewHolder: PostsAdapterViewHolder, currentPost: Post) {
        if (currentPost.isLikedByCurrentUser) {
            postsAdapterViewHolder
                    .mLikeButton.background = postsAdapterViewHolder.itemView.context
                    .applicationContext.getDrawable(R.drawable.like_button_active)
        } else {
            postsAdapterViewHolder
                    .mLikeButton.background = postsAdapterViewHolder.itemView.context
                    .applicationContext.getDrawable(R.drawable.like_button)
        }
    }
}