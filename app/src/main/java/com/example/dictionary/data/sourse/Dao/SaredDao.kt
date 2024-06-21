package com.example.dictionary.data.sourse.Dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.dictionary.data.model.Stared
@Dao
interface SaredDao {
    @Query("SELECT * FROM stared where id = :id")
    fun getStared(id: Long): Stared
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun updateStared(stared: Stared)
}