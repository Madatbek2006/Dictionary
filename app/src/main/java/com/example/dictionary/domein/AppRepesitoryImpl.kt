package com.example.dictionary.domein

import android.database.Cursor
import com.example.dictionary.data.model.Dictionary
import com.example.dictionary.data.sourse.MyDatabase
import com.example.dictionary.utils.myLog

object AppRepesitoryImpl:AppRepesitory {


    val dao= MyDatabase.getInstance().dictionaryDao()
    override fun getUzDictionary(text: String): Cursor {
        return dao.getSearchTextUz(text)
    }

    override fun getEngDictionary(text: String): Cursor {
       return dao.getSearchTextEng(text)
    }

    override fun updateDictionary(dictionary: Dictionary) = dao.updateDictionary(dictionary)

    override fun getBookmarkDictionary(): Cursor = dao.getBookmarkDictionary()
    override fun getFull(): Cursor = dao.getText()
    override fun getEngText(text: String):String{
        var cursor:Cursor= dao.getEngText(text)
        cursor.moveToFirst()
        cursor.let {
            var uzb="Topilmadi"
            while (!it.isAfterLast){
                uzb=it.getString(it.getColumnIndexOrThrow("english"))
                it.moveToNext()
            }

            return uzb
        }
    }

    override fun getUzbText(text: String): String{
        var cursor:Cursor= dao.getUzbText(text)
        cursor.moveToFirst()
        cursor.let {
            var uzb="Topilmadi"
            while (!it.isAfterLast){
                uzb=it.getString(it.getColumnIndexOrThrow("uzbek"))
                it.moveToNext()
            }

            return uzb
        }
    }

    override fun getCurrentWord(id: Long): Dictionary = dao.getDictionary(id)


}