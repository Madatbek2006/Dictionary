package com.example.dictionary.presenter.screen.main

import android.database.Cursor
import com.example.dictionary.data.model.Dictionary
import com.example.dictionary.domein.AppRepesitory
import com.example.dictionary.domein.AppRepesitoryImpl

class MainModel: MainController.Model {
    private val appRepesitory:AppRepesitory=AppRepesitoryImpl
    override fun getCursor(text: String, boolean: Boolean): Cursor {
        if (boolean){
            return appRepesitory.getUzDictionary(text)
        }
        return appRepesitory.getEngDictionary(text)
    }

    override fun getFull(boolean: Boolean):Cursor{

        if (boolean){
            return appRepesitory.getUzb()
        }
        return appRepesitory.getFull()
    }
    override fun getCurrentWord(id: Long): Dictionary =appRepesitory.getCurrentWord(id)

}