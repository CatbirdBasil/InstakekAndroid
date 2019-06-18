package com.mobiledev.edu.instakek.data.database.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query
import com.mobiledev.edu.instakek.data.database.entity.Channel

@Dao
interface ChannelDao : GenericDao<Channel> {

    @Query("SELECT * FROM channel")
    override fun getAll(): LiveData<List<Channel>>

    @Query("SELECT * FROM channel WHERE id = :id")
    override fun getById(id: Long): LiveData<Channel>

    @Query("SELECT * FROM channel WHERE id = (SELECT channel_id FROM post WHERE :id)")
    fun getChannelByPostId(id: Long): Channel

    @Query("SELECT * FROM channel WHERE creator_id = :id")
    fun getBaseChannelByUserId(id: Long): LiveData<Channel>

}