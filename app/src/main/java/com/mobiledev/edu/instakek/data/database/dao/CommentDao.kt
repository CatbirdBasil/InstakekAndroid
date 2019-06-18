package com.mobiledev.edu.instakek.data.database.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query
import com.mobiledev.edu.instakek.data.database.entity.Comment

@Dao
interface CommentDao : GenericDao<Comment> {

    @Query("SELECT * FROM comment")
    override fun getAll(): LiveData<List<Comment>>

    @Query("SELECT * FROM comment WHERE id = :id")
    override fun getById(id: Long): LiveData<Comment>

    @Query("SELECT * FROM comment WHERE post_id = :id")
    fun getCommentsByPostId(id: Long): LiveData<List<Comment>>
}