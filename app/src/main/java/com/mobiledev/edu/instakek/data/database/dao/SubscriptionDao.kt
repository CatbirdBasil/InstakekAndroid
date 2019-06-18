package com.mobiledev.edu.instakek.data.database.dao

import android.arch.persistence.room.*
import com.mobiledev.edu.instakek.data.database.entity.Subscription

@Dao
interface SubscriptionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSubscription(s: Subscription)

    @Update
    fun updateSubscription(s: Subscription)

    @Delete
    fun deleteSubscription(s: Subscription)

    @Query("SELECT * FROM subscription WHERE channel_id = :id")
    fun getSubscriptionByChanellId(id: Int): List<Subscription>

    @Query("SELECT * FROM subscription WHERE user_id = :id")
    fun getSubscriptionByUserId(id: Int): List<Subscription>

    @Query("SELECT * FROM subscription WHERE user_id = :id AND is_active = 1")
    fun getActiveSubscriptionByUserId(id: Int): List<Subscription>


    @Query("SELECT * FROM subscription")
    fun getSubscriptions(): List<Subscription>


}