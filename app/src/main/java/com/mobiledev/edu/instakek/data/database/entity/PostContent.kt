package com.mobiledev.edu.instakek.data.database.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.PrimaryKey


@Entity(
        tableName = "post_content",
        foreignKeys = arrayOf(
                ForeignKey(
                        entity = Post::class,
                        parentColumns = arrayOf("id"),
                        childColumns = arrayOf("post_id"),
                        onDelete = ForeignKey.NO_ACTION
                )
        )
)
class PostContent (
        @PrimaryKey(autoGenerate = false)
    var id: Long?,
        @ColumnInfo(name = "post_id")
    var postId: Long,
        @ColumnInfo(name = "content_link")
    var contentLink: String
)
{}