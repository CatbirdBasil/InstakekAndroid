package com.mobiledev.edu.instakek.data.repository

import android.arch.lifecycle.LiveData
import com.mobiledev.edu.instakek.data.database.entity.Post

interface PostRepository : BaseRepository<Post> {
    fun getPostsFromSubscribedChannels(): LiveData<List<Post>>
    fun getPostWithComments(postId: Long): LiveData<Post>

    fun likePost(postId: Long)
    fun dislikePost(postId: Long)
}