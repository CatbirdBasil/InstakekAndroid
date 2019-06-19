package com.mobiledev.edu.instakek.ui.viewModel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import com.mobiledev.edu.instakek.data.database.entity.Channel
import com.mobiledev.edu.instakek.data.repository.ChannelRepository
import com.mobiledev.edu.instakek.data.repository.impl.ChannelRepositoryImpl

class ChannelViewModel(application: Application) : AndroidViewModel(application) {

    private val channelRepository: ChannelRepository

    init {
        channelRepository = ChannelRepositoryImpl(application)
    }

    fun invalidateData() = channelRepository.invalidateData()
    fun isFetchingData() = channelRepository.isCurrentlyFetchingData()

    fun getCurrentUserBaseChannel(): LiveData<Channel> {
        return channelRepository.getCurrentUserBaseChannel()
    }
}