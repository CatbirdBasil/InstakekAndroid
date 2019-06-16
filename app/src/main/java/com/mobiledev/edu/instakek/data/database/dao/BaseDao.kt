package com.mobiledev.edu.instakek.data.database.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Update
import com.mobiledev.edu.instakek.data.database.entity.User

interface BaseDao<T> {
    fun getAll(): LiveData<List<T>>

    fun getById(id: Long): LiveData<T>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(obj: T): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg users: User): LongArray

    @Delete
    suspend fun delete(obj: T)

    @Update
    suspend fun update(obj: T)
}