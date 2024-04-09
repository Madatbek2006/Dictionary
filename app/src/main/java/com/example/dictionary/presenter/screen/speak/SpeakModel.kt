package com.example.dictionary.presenter.screen.speak

import com.example.dictionary.domein.AppRepesitory
import com.example.dictionary.domein.AppRepesitoryImpl

class SpeakModel:SpeakController.Model {

    private val appRepesitory:AppRepesitory=AppRepesitoryImpl
    override fun getEngText(text: String):String = appRepesitory.getEngText(text)

    override fun getUzbText(text: String):String = appRepesitory.getUzbText(text)
}