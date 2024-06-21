package com.example.dictionary.data.sourse

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.dictionary.data.model.Stared
import com.example.dictionary.data.sourse.Dao.Dao
import com.example.dictionary.data.sourse.Dao.SaredDao

@Database(entities = [Stared::class], version = 1, exportSchema = false)
abstract class MyDatabase2:RoomDatabase() {
    abstract fun dictionaryDao(): SaredDao;
    companion object{
        private var instanse: MyDatabase2?=null

        fun innit(context: Context): MyDatabase2 {
            return instanse ?: synchronized(this){
                instanse ?: buildDatabase(context).also { instanse =it }
            }
        }
        fun getInstance(): MyDatabase2 {
            return instanse!!
        }
        private fun buildDatabase(context: Context): MyDatabase2 {
            return Room.databaseBuilder(context.applicationContext,
                MyDatabase2::class.java,
                "Data2.db")
                .allowMainThreadQueries()
                .build()
        }
    }
}