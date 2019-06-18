package com.mobiledev.edu.instakek.data.database.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey

@Entity(
    tableName = "subscription",
    foreignKeys = arrayOf(
        ForeignKey(
            entity = Channel::class,
            parentColumns = arrayOf("id"),
                childColumns = arrayOf("channel_id"),
            onDelete = ForeignKey.NO_ACTION
        )
//            ,
//        ForeignKey(
//            entity = User::class,
//            parentColumns = arrayOf("id"),
//            childColumns = arrayOf("user_id"),
//            onDelete = ForeignKey.NO_ACTION
//        )
    ), primaryKeys = ["channel_id", "user_id"]

)
class Subscription(
//    @PrimaryKey(autoGenerate = false)
        @ColumnInfo(name = "channel_id")
        var channelId: Long,
//    @PrimaryKey(autoGenerate = false)
        @ColumnInfo(name = "user_id")
        var userId: Long,
        @ColumnInfo(name = "is_active")
    var isActive: Boolean
)