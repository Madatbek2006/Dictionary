package com.example.dictionary.presenter.screen.main

import com.example.dictionary.data.model.Dictionary
import com.example.dictionary.presenter.screen.home.HomeController

class MainPresenter(val view: MainController.View): MainController.Presenter {
    val model: MainController.Model= MainModel()
    override fun loadFull(boolean: Boolean) {
        view.showDictionary(model.getFull(boolean))
    }

    override fun loadDictionary(text: String,boolean: Boolean) {
        view.showDictionary(model.getCursor(text,boolean))
    }

    override fun onClickBookMark() {
    }

    override fun getCurrentWord(id: Long): Dictionary= model.getCurrentWord(id)

}