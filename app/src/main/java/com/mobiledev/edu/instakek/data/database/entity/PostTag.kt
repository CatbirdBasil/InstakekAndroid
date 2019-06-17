package com.mobiledev.edu.instakek.data.database.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.PrimaryKey
import java.sql.Date



@Entity(
        tableName = "post_tag",
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
                ),
                ForeignKey(
                        entity = Tag::class,
                        parentColumns = arrayOf("id"),
                        childColumns = arrayOf("tag_id"),
                        onDelete = ForeignKey.NO_ACTION
                )
        )
)
class PostTag (
        @PrimaryKey(autoGenerate = false)
        @ColumnInfo(name = "post_id")
        var postId: Long?,
        @PrimaryKey(autoGenerate = false)
        @ColumnInfo(name = "tag_id")
        var tagId: Long,
        @ColumnInfo(name = "user_id")
        var userId: Long
        )
{}