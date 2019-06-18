package com.mobiledev.edu.instakek.data.database.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey


@Entity(
        tableName = "user_tag",
        foreignKeys = arrayOf(
                ForeignKey(
                        entity = Tag::class,
                        parentColumns = arrayOf("id"),
                        childColumns = arrayOf("tag_id"),
                        onDelete = ForeignKey.NO_ACTION
                ),
                ForeignKey(
                        entity = User::class,
                        parentColumns = arrayOf("id"),
                        childColumns = arrayOf("user_id"),
                        onDelete = ForeignKey.NO_ACTION
                )/*,
                ForeignKey(
                        entity = UserTagType::class,
                        parentColumns = arrayOf("id"),
                        childColumns = arrayOf("tag_type_id"),
                        onDelete = ForeignKey.NO_ACTION
                )*/
        ), primaryKeys = ["user_id", "tag_id"]
)
class UserTag (
    @ColumnInfo(name = "user_id")
//    @PrimaryKey(autoGenerate = false)
    var userId: Long,
//    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "tag_id")
    var tagId: Long,
    @ColumnInfo(name = "tag_type_id")
    var tagTypeId: Long

)
{}