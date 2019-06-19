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
import com.mobiledev.edu.instakek.data.network.utils.NetworkUtils
import com.mobiledev.edu.instakek.data.repository.FetchingRepository
import com.mobiledev.edu.instakek.data.repository.UserRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserRepositoryImpl(val context: Context) : UserRepository, FetchingRepository() {

    companion object {
        private val TAG = UserRepositoryImpl::class.qualifiedName
    }
    private val userDao: UserDao = AppDatabase.getAppDataBase(context)!!.userDao()
    private val userApi: UserRequests = ApiEndpoints.User

    override fun getAll(): LiveData<List<User>> {
        Log.d(TAG, "Attempting to fetch users")

        if (!isRecent && !isFetchingData && NetworkUtils.isOnline(context.applicationContext)) {
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

    private fun insertAsync(user: User) {
        AsyncTask.execute {
            userDao.insert(user)
        }
    }

    override fun getById(id: Long): LiveData<User> {
        Log.d(TAG, "Attempting to fetch user(id = $id)")

        if (!isRecent && !isFetchingData) {

            isFetchingData = true
            userApi.getById(id).enqueue(object : Callback<User> {

                override fun onResponse(call: Call<User>?, response: Response<User>?) {
                    if (response!!.isSuccessful) {
                        insertAsync(response.body())
                        isRecent = true
                    } else {
                        Log.d(UserRepositoryImpl.TAG, "Error occurred while fetching user. Error code: ${response.code()}")
                    }
                    isFetchingData = false
                }

                override fun onFailure(call: Call<User>?, t: Throwable?) {
                    Log.d(UserRepositoryImpl.TAG, "Error occurred while fetching user", t)
                    isFetchingData = false
                }
            })
        }

        return userDao.getUserById(id)
    }

    override fun insert(user: User) {
        insertAsync(user)
    }

    override fun update(user: User) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun delete(id: Long) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}