package com.mobiledev.edu.instakek.data.database.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.PrimaryKey
import java.util.*


@Entity(
    tableName = "chanell",
    foreignKeys = arrayOf(
        ForeignKey(
            entity = User::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("creator_id"),
            onDelete = ForeignKey.NO_ACTION
        ),
        ForeignKey(
            entity = ChanellType::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("chanell_type_id"),
            onDelete = ForeignKey.NO_ACTION
        )
    )
)
class Chanell(
    @PrimaryKey(autoGenerate = true)
    var id: Long?,
    @ColumnInfo(name = "creator_id")
    var creatorId: Long,
    @ColumnInfo(name = "chanell_type_id")
    var chanelTyre: Long,
    @ColumnInfo(name = "chanell_name")
    var chanelName: String,
    @ColumnInfo(name = "creation_date")
    var creationDate: Date,
    @ColumnInfo(name = "img_src")
    var imgSrc: String
)