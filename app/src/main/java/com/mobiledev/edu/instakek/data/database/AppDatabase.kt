package com.mobiledev.edu.instakek.data.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import android.content.Context
import com.mobiledev.edu.instakek.data.database.entity.Chanell
import com.mobiledev.edu.instakek.data.database.entity.ChanellType
import com.mobiledev.edu.instakek.data.database.entity.ChatMessage
import com.mobiledev.edu.instakek.data.database.entity.User


@Database(entities = [User::class, Chanell::class, ChanellType::class, ChatMessage::class], version = 1)
@TypeConverters(DateTypeConverter::class)
abstract class AppDatabase : RoomDatabase() {
    //abstract fun userDao(): UserDao
    //abstract fun subscriptionDao(): SubscriptionDao?



    companion object {
        var INSTANCE: AppDatabase? = null

        fun getAppDataBase(context: Context): AppDatabase? {
            if (INSTANCE == null) {
                synchronized(AppDatabase::class) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, "myDB").build()
                }
            }
            return INSTANCE
        }

        fun destroyDataBase() {
            INSTANCE = null
        }
    }
}