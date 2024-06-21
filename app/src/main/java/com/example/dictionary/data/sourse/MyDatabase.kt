package com.example.dictionary.data.sourse

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.dictionary.data.model.Dictionary
import com.example.dictionary.data.model.Stared
import com.example.dictionary.data.sourse.Dao.Dao
import java.lang.Appendable

@Database(entities = [Dictionary::class], version = 1, exportSchema = false)
abstract class MyDatabase:RoomDatabase() {
    abstract fun dictionaryDao(): Dao;
    companion object{
        private var instanse:MyDatabase?=null

        fun innit(context: Context):MyDatabase{
            return instanse ?: synchronized(this){
                instanse ?: buildDatabase(context).also { instanse=it }
            }
        }
        fun getInstance():MyDatabase{
            return instanse!!
        }
        private fun buildDatabase(context: Context):MyDatabase{
            return Room.databaseBuilder(context.applicationContext,
                MyDatabase::class.java,
                "Data1.db")
                .allowMainThreadQueries()
                .createFromAsset("dictionary.db")
                .build()
        }
    }
}