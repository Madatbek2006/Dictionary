package com.example.dictionary.domein

import android.database.Cursor
import com.example.dictionary.data.model.Dictionary

interface AppRepesitory {
    fun getUzDictionary(text:String):Cursor
    fun getEngDictionary(text:String):Cursor
    fun updateDictionary(dictionary: Dictionary)
    fun getBookmarkDictionary():Cursor
    fun getFull():Cursor
    fun getEngText(text:String):String
    fun getUzbText(text:String):String
    fun getCurrentWord(id:Long):Dictionary
     fun getUzb(): Cursor
}