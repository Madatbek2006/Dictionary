package com.example.dictionary.data.sourse.Dao

import android.database.Cursor
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.dictionary.data.model.Dictionary
import com.example.dictionary.data.model.Stared

@Dao
interface Dao {

    @Query("SELECT *FROM dictionary WHERE dictionary.english LIKE :key || '%'")
    fun getSearchTextEng(key:String):Cursor
//    @Query("SELECT *FROM dictionary WHERE dictionary.uzbek LIKE :key || '%'")
    @Query("SELECT * FROM dictionary WHERE dictionary.uzbek LIKE :key || '%' ORDER BY dictionary.uzbek asc")
    fun getSearchTextUz(key:String):Cursor

    @Query("SELECT *FROM dictionary")
    fun getText():Cursor
    @Query("SELECT *FROM dictionary  ORDER BY dictionary.uzbek COLLATE NOCASE")
    fun getUzb():Cursor

    @Update
    fun updateDictionary(dictionary: Dictionary)


    @Query("SELECT * FROM dictionary WHERE dictionary.is_favourite = 1")
    fun getBookmarkDictionary():Cursor
    @Query("SELECT * FROM dictionary WHERE dictionary.uzbek=:text LIMIT 1")
    fun getEngText(text:String):Cursor
    @Query("SELECT * FROM dictionary WHERE dictionary.english=:text LIMIT 1")
    fun getUzbText(text:String):Cursor
    @Query("SELECT * FROM dictionary WHERE dictionary.id=:id LIMIT 1")
    fun getDictionary(id:Long):Dictionary

    @Insert
    fun setNewWord(dictionary: Dictionary):Long

}