package com.mobiledev.edu.instakek.data.database.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*

@Dao
interface GenericDao<T> {
    fun getAll(): LiveData<List<T>>

    fun getById(id: Long): LiveData<T>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    @JvmSuppressWildcards
    fun insert(obj: T): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    @JvmSuppressWildcards
    fun insertAll(vararg objects: T): LongArray

    @Delete
    @JvmSuppressWildcards
    fun delete(obj: T)

    @Update
    @JvmSuppressWildcards
    fun update(obj: T)
}