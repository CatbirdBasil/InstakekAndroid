package com.mobiledev.edu.instakek.data.database.dao

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query
import com.mobiledev.edu.instakek.data.database.entity.User

@Dao
interface UserDao : GenericDao<User> {

    @Query("SELECT * FROM USER")
    override fun getAll(): LiveData<List<User>>

    @Query("SELECT * FROM USER WHERE id = :id")
    override fun getById(id: Long): LiveData<User>

    @Query("SELECT * FROM USER WHERE name = :name")
    fun getUsersByName(name: String): LiveData<List<User>>

    @Query("SELECT * FROM USER JOIN likes ON USER.id = likes.user_id WHERE likes.post_id = :id")
    fun getUsersByLikedPostId(id: Long): LiveData<List<User>>

    @Query("SELECT * FROM USER JOIN comment ON USER.id = comment.user_id WHERE comment.id = :id")
    fun getUserByCommentId(id: Long): LiveData<User>


    //    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    fun insertUser(user: User)
//
//    @Update
//    fun updateUser(user: User)
//
//    @Delete
//    fun deleteUser(user: User)

    @Query("SELECT * FROM USER")
    fun getUsers(): LiveData<List<User>>

    @Query("SELECT * FROM USER WHERE id == :id")
    fun getUserById(id: Long): LiveData<User>

    //----------------------
    @Query("SELECT * FROM USER LIMIT :limit OFFSET :offset")
    operator fun get(offset: Int, limit: Int): LiveData<List<User>>

//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    fun insertAll(vararg users:User): LongArray
    //----------------------

}