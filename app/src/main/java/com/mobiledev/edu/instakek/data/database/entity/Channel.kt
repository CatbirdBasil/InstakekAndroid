package com.mobiledev.edu.instakek.data.database.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.PrimaryKey
import java.util.*

@Entity(
        tableName = "channel"/*,
    foreignKeys = arrayOf(
        /*ForeignKey(
            entity = User::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("creator_id"),
            onDelete = ForeignKey.NO_ACTION
        )*//*,
        ForeignKey(
            entity = ChannelType::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("channel_type_id"),
            onDelete = ForeignKey.NO_ACTION
        )*/
    )*///TODO RUN FK
)
class Channel(
        @PrimaryKey(autoGenerate = false)
    var id: Long?,
        @ColumnInfo(name = "creator_id")
    var creatorId: Long,
        @ColumnInfo(name = "channel_type_id")
        var channelType: Long,
        @ColumnInfo(name = "channel_name")
    var channelName: String,
        @ColumnInfo(name = "creation_date")
    var creationDate: Date,
        @ColumnInfo(name = "img_src")
    var imgSrc: String?

) {
    @Ignore
    var posts: List<Post>? = null

    @Ignore
    var creator: User? = null

    @Ignore
    var subscribers: List<User>? = null
}