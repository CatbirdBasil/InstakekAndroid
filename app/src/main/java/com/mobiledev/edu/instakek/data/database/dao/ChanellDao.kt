package com.mobiledev.edu.instakek.data.database.dao

import android.arch.persistence.room.*
import com.mobiledev.edu.instakek.data.database.entity.Chanell


interface ChanellDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertChanell(chanell: Chanell)

    @Update
    fun updateChanell(chanell: Chanell)

    @Delete
    fun deleteChanell(chanell: Chanell)


    @Query("SELECT * FROM chanell")
    fun getSubscriptions(): List<Chanell>

}