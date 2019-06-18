package com.mobiledev.edu.instakek.data.repository

abstract class FetchingRepository {

    protected var isRecent: Boolean = false
    protected var isFetchingData: Boolean = false

    fun invalidateData() {
        isRecent = false
    }

    fun isCurrentlyFetchingData(): Boolean = isFetchingData
}