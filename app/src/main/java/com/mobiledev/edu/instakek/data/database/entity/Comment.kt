package com.mobiledev.edu.instakek.data.database.entity

import android.arch.persistence.room.*
import java.util.*


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
data class Comment(
        @PrimaryKey(autoGenerate = false)
        var id: Long,
        @ColumnInfo(name = "post_id")
        var postId: Long,
        @ColumnInfo(name = "user_id")
        var userId: Long,
        @ColumnInfo(name = "comment_time")
        var commentTime: Date,
        @ColumnInfo(name = "comment_text")
        var commentText: String
) {
    @Ignore
    var user: User? = null;
}