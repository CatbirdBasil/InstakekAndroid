package com.mobiledev.edu.instakek.data.database.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Query
import com.mobiledev.edu.instakek.data.database.entity.Post

interface PostDao : GenericDao<Post> {

    @Query("SELECT * FROM channel")
    override fun getAll(): LiveData<List<Post>>

    @Query("SELECT * FROM channel WHERE id = :id")
    override fun getById(id: Long): LiveData<Post>
}
