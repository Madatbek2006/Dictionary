package com.example.dictionary.app

import android.app.Application
import com.example.dictionary.data.sourse.MyDatabase
import com.example.dictionary.data.sourse.MyDatabase2

class App:Application() {
    override fun onCreate() {
        super.onCreate()
        MyDatabase.innit(this)
        MyDatabase2.innit(this)
    }
}