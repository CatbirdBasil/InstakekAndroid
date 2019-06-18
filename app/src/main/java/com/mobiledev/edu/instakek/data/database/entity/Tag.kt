package com.mobiledev.edu.instakek.data.database.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "tag")
class Tag (
        @PrimaryKey(autoGenerate = false)
        var id: Long,
        @ColumnInfo(name = "tag_text")
    var tagText: String
)
{}