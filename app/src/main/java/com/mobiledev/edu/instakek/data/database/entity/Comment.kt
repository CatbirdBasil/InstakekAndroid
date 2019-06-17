package com.mobiledev.edu.instakek.data.database.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.PrimaryKey
import java.sql.Date


@Entity(
        tableName = "comment",
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
        )
)
class Comment (
        @PrimaryKey(autoGenerate = true)
        var id: Long?,
        @ColumnInfo(name = "post_id")
        var postId: Long,
        @ColumnInfo(name = "user_id")
        var userId: Long,
        @ColumnInfo(name = "comment_time")
        var commentTime: Date,
        @ColumnInfo(name = "comment_text")
        var commentText: String )
{}