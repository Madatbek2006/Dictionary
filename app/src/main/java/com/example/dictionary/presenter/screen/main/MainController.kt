package com.example.dictionary.presenter.screen.main

import android.database.Cursor
import com.example.dictionary.data.model.Dictionary

interface MainController {
    interface View{
        fun showDictionary(cursor: Cursor)
    }
    interface Model{
        fun getCursor(text: String,boolean: Boolean):Cursor
        fun getFull(boolean: Boolean):Cursor
        fun getCurrentWord(id:Long):Dictionary
    }
    interface Presenter{
        fun loadFull(boolean: Boolean)
        fun loadDictionary(text: String,boolean: Boolean)
        fun onClickBookMark()
        fun getCurrentWord(id:Long):Dictionary
    }
}