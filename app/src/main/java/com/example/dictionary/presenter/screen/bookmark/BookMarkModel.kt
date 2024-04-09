package com.example.dictionary.presenter.screen.bookmark

import android.database.Cursor
import com.example.dictionary.data.model.Dictionary
import com.example.dictionary.domein.AppRepesitory
import com.example.dictionary.domein.AppRepesitoryImpl

class BookMarkModel :BookMarkController.Model{
    private val appRepesitory:AppRepesitory=AppRepesitoryImpl
    override fun getCursor(): Cursor =appRepesitory.getBookmarkDictionary()
    override fun getCurrentWord(id: Long): Dictionary {
       return appRepesitory.getCurrentWord(id)
    }
}