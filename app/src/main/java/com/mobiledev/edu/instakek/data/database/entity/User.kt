package com.mobiledev.edu.instakek.data.database.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.PrimaryKey
import java.util.*

@Entity(tableName = "USER")
class User(
        @PrimaryKey(autoGenerate = false)
        var id: Long,

        @ColumnInfo(name = "username")
        var username: String,

        @ColumnInfo(name = "email")
        var email: String,

        @ColumnInfo(name = "name")
        var name: String,

        @ColumnInfo(name = "name")
        var surname: String,

        @ColumnInfo(name = "registration_date")
        var registrationDate: Date,

        @ColumnInfo(name = "img_src")
        var imgSrc: String?,

        @ColumnInfo(name = "is_active")
        var isActive: Boolean
) {
    @Ignore
    var mainChannel: Channel? = null
}