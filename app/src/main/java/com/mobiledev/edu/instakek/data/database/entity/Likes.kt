package com.mobiledev.edu.instakek.data.database.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey

@Entity(
        tableName = "likes",
        foreignKeys = arrayOf(
                ForeignKey(
                        entity = Post::class,
                        parentColumns = arrayOf("id"),
                        childColumns = arrayOf("post_id"),
                        onDelete = ForeignKey.NO_ACTION
                ),
                ForeignKey(
                        entity = User::class,
                        parentColumns = arrayOf("id"),
                        childColumns = arrayOf("user_id"),
                        onDelete = ForeignKey.NO_ACTION
                )
        ), primaryKeys = ["post_id", "user_id"]
)
class Likes (
//    @PrimaryKey(autoGenerate = false)
        @ColumnInfo(name = "user_id")
        var userId: Long,
//    @PrimaryKey(autoGenerate = false)
        @ColumnInfo(name = "post_id")
    var postId: Long
)
{}