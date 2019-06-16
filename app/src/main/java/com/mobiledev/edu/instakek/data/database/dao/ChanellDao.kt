package com.mobiledev.edu.instakek.data.database.dao

import android.arch.persistence.room.*
import com.mobiledev.edu.instakek.data.database.entity.Channel


interface ChanellDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertChanell(channel: Channel)

    @Update
    fun updateChanell(channel: Channel)

    @Delete
    fun deleteChanell(channel: Channel)


    @Query("SELECT * FROM channel")
    fun getSubscriptions(): List<Channel>

}