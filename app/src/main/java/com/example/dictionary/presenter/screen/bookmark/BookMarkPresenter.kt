package com.example.dictionary.presenter.screen.bookmark

import com.example.dictionary.data.model.Dictionary

class BookMarkPresenter(private val view: BookMarkController.View):BookMarkController.Presenter {
    val model:BookMarkController.Model=BookMarkModel()
    override fun loadDictionary() {
        view.showDictionary(model.getCursor())
    }

    override fun onClickBack() {
        view.back()
    }

    override fun getCurrentWord(id: Long): Dictionary {
        return model.getCurrentWord(id)
    }
}