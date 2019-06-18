package com.mobiledev.edu.instakek.ui.viewModel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import com.mobiledev.edu.instakek.data.database.entity.Post
import com.mobiledev.edu.instakek.data.repository.PostRepository
import com.mobiledev.edu.instakek.data.repository.impl.PostRepositoryImpl

class PostViewModel(application: Application) : AndroidViewModel(application) {

    private val postRepository: PostRepository

    init {
        postRepository = PostRepositoryImpl(application)
    }

    fun invalidateData() {
        postRepository.invalidateData()
    }

    fun getSubscribedPosts(): LiveData<List<Post>> {
        return postRepository.getPostsFromSubscribedChannels()
    }

}