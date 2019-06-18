package com.mobiledev.edu.instakek.ui.viewModel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import com.mobiledev.edu.instakek.data.database.entity.User
import com.mobiledev.edu.instakek.data.repository.UserRepository
import com.mobiledev.edu.instakek.data.repository.impl.UserRepositoryImpl

class UserViewModel(application: Application): AndroidViewModel(application) {

    private val userRepository: UserRepository

    init {
        userRepository = UserRepositoryImpl(application)
    }

    fun invalidateData() {
        userRepository.invalidateData()
    }

    fun getUsers(): LiveData<List<User>> {
        return userRepository.getAll()
    }

}