package com.example.dictionary.presenter.screen.speak

import android.database.Cursor

interface SpeakController {
    interface View{
    }
    interface Model{
        fun getEngText(text:String):String
        fun getUzbText(text:String):String
    }
    interface Presenter{
        fun loadText(boll:Boolean,text:String)
    }
}