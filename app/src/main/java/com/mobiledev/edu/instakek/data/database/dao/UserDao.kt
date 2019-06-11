package com.mobiledev.edu.instakek.data.database.dao

import android.arch.persistence.room.*
import android.arch.persistence.room.OnConflictStrategy
import android.arch.lifecycle.LiveData
import com.mobiledev.edu.instakek.data.database.entity.User


@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(user: User)

    @Update
    fun updateUser(user: User)

    @Delete
    fun deleteUser(user: User)

    @Query("SELECT * FROM USER WHERE name == :name")
    fun getUsersByName(name: String): LiveData<List<User>>

    @Query("SELECT * FROM USER")
    fun getUsers(): LiveData<List<User>>

    @Query("SELECT * FROM USER WHERE id == :id")
    fun getUserById(id: Int): LiveData<User>

    //----------------------
    @Query("SELECT * FROM USER LIMIT :limit OFFSET :offset")
    operator fun get(offset: Int, limit: Int): LiveData<List<User>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg users:User): LongArray
    //----------------------

}