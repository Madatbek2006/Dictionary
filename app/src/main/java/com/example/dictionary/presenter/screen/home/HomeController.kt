package com.example.dictionary.presenter.screen.home

import android.database.Cursor

interface HomeController {
    interface View{
        fun openBookMarkFragment()

    }
    interface Model{

    }
    interface Presenter{
        fun getCursor(): Cursor
    }
}