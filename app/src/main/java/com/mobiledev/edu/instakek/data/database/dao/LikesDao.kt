package com.mobiledev.edu.instakek.data.database.dao

import android.arch.persistence.room.*
import com.mobiledev.edu.instakek.data.database.entity.Likes
import com.mobiledev.edu.instakek.data.database.entity.User

@Dao
interface LikesDao {

    @Query("SELECT * FROM USER JOIN likes ON USER.id = likes.user_id WHERE likes.post_id = :id")
    fun getLikedUsersByPostId(id: Long): List<User>

    @Query("SELECT COUNT(*) FROM USER JOIN likes ON USER.id = likes.user_id WHERE likes.post_id = :id")
    fun countLikedUsersByPostId(id: Long): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    @JvmSuppressWildcards
    fun insert(like: Likes): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    @JvmSuppressWildcards
    fun insertAll(vararg likes: Likes): LongArray

    @Delete
    @JvmSuppressWildcards
    fun delete(like: Likes)

    @Update
    @JvmSuppressWildcards
    fun update(like: Likes)
}