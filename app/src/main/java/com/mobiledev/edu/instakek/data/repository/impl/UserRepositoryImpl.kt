package com.mobiledev.edu.instakek.data.repository.impl

import android.arch.lifecycle.LiveData
import android.content.Context
import android.os.AsyncTask
import android.util.Log
import com.mobiledev.edu.instakek.data.database.AppDatabase
import com.mobiledev.edu.instakek.data.database.dao.UserDao
import com.mobiledev.edu.instakek.data.database.entity.User
import com.mobiledev.edu.instakek.data.network.requestApi.UserRequests
import com.mobiledev.edu.instakek.data.network.utils.ApiEndpoints
import com.mobiledev.edu.instakek.data.repository.UserRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserRepositoryImpl(context: Context) : UserRepository {

    companion object {
        private val TAG = UserRepositoryImpl::class.qualifiedName
    }
    private val userDao: UserDao = AppDatabase.getAppDataBase(context)!!.userDao()
    private val userApi: UserRequests = ApiEndpoints.User

    private var isRecent: Boolean = false
    private var isFetchingData: Boolean = false

    override fun invalidateData() {
        isRecent = false
    }

    override fun getAll(): LiveData<List<User>> {
        Log.d(TAG, "Attempting to fetch users")

        if (!isRecent && !isFetchingData) {
            Log.d(TAG, "Attempting to fetch users from api")

            val usersCallback = userApi.getAll()
            isFetchingData = true

            usersCallback.enqueue(object : Callback<List<User>> {

                override fun onResponse(call: Call<List<User>>?, response: Response<List<User>>?) {
                    if (response!!.isSuccessful) {
                        insertAsync(response.body())
                        isRecent = true
                    } else {
                        Log.d(UserRepositoryImpl.TAG, "Error occurred while fetching users. Error code: ${response.code()}")
                    }
                    isFetchingData = false
                }

                override fun onFailure(call: Call<List<User>>?, t: Throwable?) {
                    Log.d(UserRepositoryImpl.TAG, "Error occurred while fetching users", t)
                    isFetchingData = false
                }
            })
        }

        return userDao.getUsers()
    }

    private fun insertAsync(list: List<User>) {
        AsyncTask.execute {
            userDao.insertAll(*list.toTypedArray())
        }
    }

    override fun getById(id: Int): LiveData<User> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun insert(user: User) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun update(user: User) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun delete(id: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}