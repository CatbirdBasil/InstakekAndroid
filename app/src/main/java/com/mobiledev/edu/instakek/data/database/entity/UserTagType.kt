package com.mobiledev.edu.instakek.data.database.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey


@Entity(
        tableName = "user_tag_type"
)
class UserTagType (
        @PrimaryKey(autoGenerate = false)
    var id: Long?,
        @ColumnInfo(name = "tag_type_name")
    var tagTypeName: String
)