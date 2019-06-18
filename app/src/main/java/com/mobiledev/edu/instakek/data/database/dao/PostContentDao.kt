package com.mobiledev.edu.instakek.data.database.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query
import com.mobiledev.edu.instakek.data.database.entity.PostContent

@Dao
interface PostContentDao : GenericDao<PostContent> {

    @Query("SELECT * FROM post_content")
    override fun getAll(): LiveData<List<PostContent>>

    @Query("SELECT * FROM post_content WHERE id = :id")
    override fun getById(id: Long): LiveData<PostContent>

    @Query("SELECT * FROM post_content WHERE post_id = :id")
    fun getByPostId(id: Long): List<PostContent>
}