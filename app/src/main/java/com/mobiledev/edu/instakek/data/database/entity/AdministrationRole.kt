package com.mobiledev.edu.instakek.data.database.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
class AdministrationRole(
        @PrimaryKey(autoGenerate = false)
    var id: Long?,
        @ColumnInfo(name = "role_name")
    var roleName: String
)