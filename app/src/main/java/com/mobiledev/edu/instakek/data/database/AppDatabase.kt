package com.mobiledev.edu.instakek.data.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import android.content.Context
import com.mobiledev.edu.instakek.data.database.dao.*
import com.mobiledev.edu.instakek.data.database.entity.*


@Database(entities = [User::class, Channel::class, /*ChannelType::class,*/ ChatMessage::class,
    Subscription::class, Post::class, PostContent::class, Likes::class, Comment::class,
    Tag::class, PostTag::class, UserTag::class/*, UserTagType::class*/], version = 9)
@TypeConverters(DateTypeConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun postDao(): PostDao
    abstract fun postContentDao(): PostContentDao
    abstract fun commentDao(): CommentDao
    abstract fun subscriptionDao(): SubscriptionDao
    abstract fun channelDao(): ChannelDao
    abstract fun likesDao(): LikesDao
//    abstract fun subscriptionDao(): SubscriptionDao?

    companion object {
        var INSTANCE: AppDatabase? = null

        fun getAppDataBase(context: Context): AppDatabase? {
            if (INSTANCE == null) {
                synchronized(AppDatabase::class) {
                    INSTANCE = Room
                            .databaseBuilder(context.applicationContext, AppDatabase::class.java, "myDB")
                            .fallbackToDestructiveMigration()
                            .build()
                }
            }
            return INSTANCE
        }

        fun destroyDataBase() {
            INSTANCE = null
        }
    }
}