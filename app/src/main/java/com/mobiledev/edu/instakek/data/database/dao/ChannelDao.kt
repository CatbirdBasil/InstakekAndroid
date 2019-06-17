package com.mobiledev.edu.instakek.data.database.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Query
import com.mobiledev.edu.instakek.data.database.entity.Channel


interface ChannelDao : GenericDao<Channel> {

    @Query("SELECT * FROM channel")
    override fun getAll(): LiveData<List<Channel>>

    @Query("SELECT * FROM channel WHERE id = :id")
    override fun getById(id: Long): LiveData<Channel>

}