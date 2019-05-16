package com.mobiledev.edu.instakek.data.database.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "chanell_type")
class ChanellType(
    @PrimaryKey(autoGenerate = true)
    var id: Long?,
    @ColumnInfo(name = "type_name")
    var type: String
) {
}
