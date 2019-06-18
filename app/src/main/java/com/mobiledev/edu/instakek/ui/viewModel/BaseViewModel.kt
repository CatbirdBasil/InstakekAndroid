package com.mobiledev.edu.instakek.ui.viewModel

import com.mobiledev.edu.instakek.data.repository.BaseRepository

abstract class BaseViewModel<T>(val repo: BaseRepository<T>) {

    fun invalidateData() {
        repo.invalidateData()
    }


}