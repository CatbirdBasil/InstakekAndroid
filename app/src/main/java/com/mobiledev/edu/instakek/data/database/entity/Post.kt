package com.mobiledev.edu.instakek.data.database.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.PrimaryKey
import java.sql.Date


@Entity(
        tableName = "post",
        foreignKeys = arrayOf(
                ForeignKey(
                        entity = Channel::class,
                        parentColumns = arrayOf("id"),
                        childColumns = arrayOf("channel_id"),
                        onDelete = ForeignKey.NO_ACTION
                ),
                ForeignKey(
                        entity = Post::class,
                        parentColumns = arrayOf("id"),
                        childColumns = arrayOf("base_post_id"),
                        onDelete = ForeignKey.NO_ACTION
                )
        )
)
class Post (
    @PrimaryKey(autoGenerate = true)
    var id: Long?,
    @ColumnInfo(name = "sender_id")
    var senderId: Long,
    @ColumnInfo(name = "channel_id")
    var channelId: Long,
    @ColumnInfo(name = "creation_time")
    var creationTime: Date,
    @ColumnInfo(name = "base_post_id")
    var basePostId: Long )
{}