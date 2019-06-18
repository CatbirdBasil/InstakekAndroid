package com.mobiledev.edu.instakek.data.repository

import android.arch.lifecycle.LiveData

interface BaseRepository<T> {

    fun invalidateData()
    fun isCurrentlyFetchingData(): Boolean

    fun getAll(): LiveData<List<T>>
    fun getById(id: Long): LiveData<T>
    fun insert(entity: T)
    fun update(entity: T)
    fun delete(id: Long)
}