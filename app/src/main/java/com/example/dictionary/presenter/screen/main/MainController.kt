package com.example.dictionary.presenter.screen.main

import android.database.Cursor
import com.example.dictionary.data.model.Dictionary

interface MainController {
    interface View{
        fun showDictionary(cursor: Cursor)
        fun openBookMarkFragment()
    }
    interface Model{
        fun getCursor(text: String,boolean: Boolean):Cursor
        fun getFull():Cursor
        fun getCurrentWord(id:Long):Dictionary
    }
    interface Presenter{
        fun loadFull()
        fun loadDictionary(text: String,boolean: Boolean)
        fun onClickBookMark()
        fun getCurrentWord(id:Long):Dictionary
    }
}