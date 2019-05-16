package com.mobiledev.edu.instakek.data.database.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.PrimaryKey

@Entity(
    tableName = "chat_message",
    foreignKeys = arrayOf(
        ForeignKey(
            entity = Chanell::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("channel_id"),
            onDelete = ForeignKey.NO_ACTION
        ),
        ForeignKey(
            entity = User::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("user_id"),
            onDelete = ForeignKey.NO_ACTION
        ),
        ForeignKey(
            entity = AdministrationRole::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("role_id"),
            onDelete = ForeignKey.NO_ACTION
        )
    )
)
class Administration(
    @PrimaryKey(autoGenerate = true)
    var id: Long?,
    @ColumnInfo(name = "channel_id")
    var channelId: Long,
    @ColumnInfo(name = "user_id")
    var userId: Long,
    @ColumnInfo(name = "role_id")
    var roleId: Long
) {}