package com.mobiledev.edu.instakek.data.database.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query
import com.mobiledev.edu.instakek.data.database.entity.Post

@Dao
interface PostDao : GenericDao<Post> {

    @Query("SELECT * FROM post")
    override fun getAll(): LiveData<List<Post>>

    @Query("SELECT * FROM post WHERE id = :id")
    override fun getById(id: Long): LiveData<Post>

    @Query("SELECT * FROM post " +
            "JOIN channel ON post.channel_id = channel.id " +
            "JOIN subscription ON channel.id = subscription.channel_id " +
            "WHERE subscription.user_id = :id " +
            "AND subscription.is_active = 1 " +
            "ORDER BY post.creation_time DESC")
    fun getPostsFromSubscribedChannels(id: Long): LiveData<List<Post>>
}
