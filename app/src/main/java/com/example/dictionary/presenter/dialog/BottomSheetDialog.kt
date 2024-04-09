package com.example.dictionary.presenter.dialog

import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.dictionary.R
import com.example.dictionary.data.model.Dictionary
import com.example.dictionary.data.sourse.MyDatabase
import com.example.dictionary.databinding.BottomShettBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.util.Locale

class BottomSheetDialog:BottomSheetDialogFragment(R.layout.bottom_shett) {
    private var _binding:BottomShettBinding?=null
    val dao= MyDatabase.getInstance().dictionaryDao()
    private val binding get() = _binding!!
    lateinit var textSpeech:TextToSpeech
    var onDestroyListener:(()->Unit)?=null

    init {

    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding=BottomShettBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        textSpeech= TextToSpeech(binding.root.context,TextToSpeech.OnInitListener {
            if (it==TextToSpeech.SUCCESS){
                textSpeech.setLanguage(Locale.ENGLISH)
            }
        })
        val pos=requireArguments().getLong("pos")
        val dictionary=dao.getDictionary(pos)

        binding.apply {
            eng.text=dictionary.english
            uzb.text=dictionary.uzbek
            if (dictionary.is_favourite==1){
                chek.setBackgroundResource(R.drawable.ic_baseline_chek)
            }else{
                chek.setBackgroundResource(R.drawable.ic_bookmark_unchek)
            }
            verb.text=dictionary.type

//            chek.setOnClickListener {
//                if (dictionary.is_favourite==1){
//                    dictionary.is_favourite=0
//                    chek.setBackgroundResource(R.drawable.ic_bookmark_unchek)
//                    saveDictionary(dictionary)
//                }else{
//                    dictionary.is_favourite=1
//                    chek.setBackgroundResource(R.drawable.ic_baseline_chek)
//                    saveDictionary(dictionary)
//                }
//            }
            speak.setOnClickListener {
                speakOut(dictionary.english)
            }

        }
    }
    private fun saveDictionary(dictionary: Dictionary){
        dao.updateDictionary(dictionary)
    }
    private fun speakOut(text:String){
        textSpeech.speak(text, TextToSpeech.QUEUE_FLUSH,null,null)
    }

    override fun onDestroy() {

        onDestroyListener?.invoke()
        super.onDestroy()

    }
}