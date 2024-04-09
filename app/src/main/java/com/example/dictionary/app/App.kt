package com.example.dictionary.app

import android.app.Application
import com.example.dictionary.data.sourse.MyDatabase

class App:Application() {
    override fun onCreate() {
        super.onCreate()
        MyDatabase.innit(this)
    }
}