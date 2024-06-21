package com.example.dictionary.presenter.screen.speak

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.dictionary.R
import com.example.dictionary.data.model.Dictionary
import com.example.dictionary.data.model.Stared
import com.example.dictionary.data.sourse.MyDatabase
import com.example.dictionary.data.sourse.MyDatabase2
import com.example.dictionary.databinding.ScreenAddWordBinding
import com.example.dictionary.utils.myLog
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class AddWordScreen:Fragment(),SpeakController.View {
    private var _binding:ScreenAddWordBinding?=null
    private val dao=MyDatabase.getInstance().dictionaryDao()
    private val daoFavor= MyDatabase2.getInstance().dictionaryDao()
    private val binding get() = _binding!!
    private var list= arrayListOf<String>("noun","verb","adv","adj","prep")
    var setOnNewWordListener:(()->Unit)?=null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding= ScreenAddWordBinding.inflate(layoutInflater)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            type.setAdapter(
                ArrayAdapter(
                    requireContext(),
                    R.layout.item_dropdown_menu,
                    list
                )
            )
            addWord.setOnClickListener {
                if (!(engTxt.text?.trim()?.isEmpty() != false || engTxt.text?.trim()?.isEmpty() != false ||type.text?.trim()?.isEmpty() != false)){

                    if (checkBox.isChecked){
                        val id=dao.setNewWord(
                        Dictionary(
                            english = engTxt.text.toString(),
                            type = type.text.toString(),
                            transcript ="\\\\",
                            uzbek = uzbTxt.text.toString(),
                            countable = "",
                            is_favourite = 1
                        )
                    )
                        daoFavor.updateStared(Stared(id=id, isStared = 1))
                    }else{
                        dao.setNewWord(
                            Dictionary(
                                english = engTxt.text.toString(),
                                type = type.text.toString(),
                                transcript ="",
                                uzbek = uzbTxt.text.toString(),
                                countable = "",
                                is_favourite = 0
                            )
                        )
                    }
                    setOnNewWordListener?.invoke()
                    lifecycleScope.launch {
                        delay(100)
                        type.setText("")
                        engTxt.setText("")
                        uzbTxt.setText("")
                    }
                }
            }

            engTxt.addTextChangedListener {
                colorAddWord()
            }
            uzbTxt.addTextChangedListener {
                colorAddWord()
            }
            type.addTextChangedListener{
              colorAddWord()
            }
        }
    }
    private fun colorAddWord()=binding.apply{
        if (!(engTxt.text?.trim()?.isEmpty() != false || engTxt.text?.trim()?.isEmpty() != false ||type.text?.trim()?.isEmpty() != false)){
            "salom1".myLog()
            addWord.setCardBackgroundColor(ColorStateList.valueOf(Color.parseColor("#350C77")))
        }else{
            "salom2".myLog()
            addWord.setCardBackgroundColor(ColorStateList.valueOf(Color.parseColor("#6C4D9F")))
        }
    }
}