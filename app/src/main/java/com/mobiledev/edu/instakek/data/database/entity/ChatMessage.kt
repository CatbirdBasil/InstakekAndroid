package com.mobiledev.edu.instakek.data.database.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.PrimaryKey
import java.sql.Date


@Entity(
    tableName = "chat_message",
    foreignKeys = arrayOf(
        ForeignKey(
            entity = User::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("sender_id"),
            onDelete = ForeignKey.NO_ACTION
        ),
        ForeignKey(
            entity = User::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("reciever_id"),
            onDelete = ForeignKey.NO_ACTION
        )
    )
)
class ChatMessage(
        @PrimaryKey(autoGenerate = false)
    var id: Long?,
        @ColumnInfo(name = "sender_id")
    var senderId: Long,
        @ColumnInfo(name = "reciever_id")
    var reciverId: Long,
        @ColumnInfo(name = "message_text")
    var messageText: String,
        @ColumnInfo(name = "message_time")
    var messageTime: Date
) {

}