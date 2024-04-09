package com.example.dictionary.presenter.screen.home

import android.database.Cursor

interface HomeController {
    interface View{
         fun showDictionary(full: Cursor)
        fun openBookMarkFragment()

    }
    interface Model{

    }
    interface Presenter{

    }
}