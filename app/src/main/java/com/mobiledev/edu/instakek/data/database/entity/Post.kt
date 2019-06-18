package com.mobiledev.edu.instakek.data.database.entity

import android.arch.persistence.room.*
import java.sql.Date

@Entity(
        tableName = "post",
        foreignKeys = arrayOf(
                ForeignKey(
                        entity = Channel::class,
                        parentColumns = arrayOf("id"),
                        childColumns = arrayOf("channel_id"),
                        onDelete = ForeignKey.NO_ACTION
                )
//                ,
//                ForeignKey(
//                        entity = Post::class,
//                        parentColumns = arrayOf("id"),
//                        childColumns = arrayOf("base_post_id"),
//                        onDelete = ForeignKey.NO_ACTION
//                )
        )
)
class Post (
        @PrimaryKey(autoGenerate = true)
    var id: Long?,
        @ColumnInfo(name = "channel_id")
    var channelId: Long,
        @ColumnInfo(name = "creation_time")
        var creationDate: Date,
        @ColumnInfo(name = "base_post_id")
        var basePostId: Long?,
        @ColumnInfo(name = "text")
        var text: String) {
    @Ignore
    var contents: List<PostContent>? = null

    @Ignore
    var tags: List<Tag>? = null

    @Ignore
    var channel: Channel? = null

    @Ignore
    var likes: List<User>? = null
}