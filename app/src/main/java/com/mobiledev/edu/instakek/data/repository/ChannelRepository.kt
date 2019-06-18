package com.mobiledev.edu.instakek.data.repository

import android.arch.lifecycle.LiveData
import com.mobiledev.edu.instakek.data.database.entity.Channel

interface ChannelRepository : BaseRepository<Channel> {
    fun getCurrentUserBaseChannel(): LiveData<Channel>
    fun getBaseChannelByUserId(id: Long): LiveData<Channel>
}