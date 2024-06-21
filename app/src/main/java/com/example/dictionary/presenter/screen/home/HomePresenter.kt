package com.example.dictionary.presenter.screen.home

import android.database.Cursor
import com.example.dictionary.domein.AppRepesitory
import com.example.dictionary.domein.AppRepesitoryImpl

class HomePresenter(private val view: HomeController.View):HomeController.Presenter {

    private val appRepesitory: AppRepesitory = AppRepesitoryImpl
    override fun getCursor(): Cursor =appRepesitory.getBookmarkDictionary()
}