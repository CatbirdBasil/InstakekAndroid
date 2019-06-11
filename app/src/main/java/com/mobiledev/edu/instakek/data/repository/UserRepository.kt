package com.mobiledev.edu.instakek.data.repository

import android.arch.lifecycle.LiveData
import com.mobiledev.edu.instakek.data.database.entity.User

interface UserRepository {

    fun invalidateData()

    fun getAll(): LiveData<List<User>>
    fun getById(id: Int): LiveData<User>
    fun insert(user: User)
    fun update(user: User)
    fun delete(id: Int)

}