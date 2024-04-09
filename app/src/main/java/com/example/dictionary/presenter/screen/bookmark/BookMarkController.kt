package com.example.dictionary.presenter.screen.bookmark

import android.database.Cursor
import com.example.dictionary.data.model.Dictionary

interface BookMarkController {
    interface View{
        fun showDictionary(cursor: Cursor)
        fun back()
    }
    interface Model{
        fun getCursor(): Cursor
        fun getCurrentWord(id:Long):Dictionary
    }
    interface Presenter{
        fun loadDictionary()
        fun onClickBack()
        fun getCurrentWord(id:Long): Dictionary
    }
}