package com.example.dictionary.presenter.screen.speak

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.dictionary.databinding.ScreenSpeakBinding
import com.example.dictionary.presenter.activity.MainActivity

class SpeakScreen:Fragment(),SpeakController.View {
    private var _binding:ScreenSpeakBinding?=null
    private val binding by lazy { _binding!! }
    private var isUz =true
    private val main by lazy { (requireActivity() as MainActivity) }
    private val presenter:SpeakController.Presenter by lazy { SpeakPresenter(this) }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding=ScreenSpeakBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            speak.setOnClickListener {
                if (isUz){
                    main.promptSpeechInput("uz-Uz")
                }else{
                    main.promptSpeechInput("eng-Eng")
                }

            }
            transfer.setOnClickListener {
                if (isUz){
                    language1.text="ENG"
                    perevod1.text="Hello"
                    perevod2.text="Salom"
                    language2.text="UZB"
                    isUz=false
                }else{
                    language2.text="ENG"
                    language1.text="UZB"
                    perevod2.text="Hello"
                    perevod1.text="Salom"
                    isUz=true
                }
            }
            main.shareData={
                var s=it.substring(1,it.length-1)
               perevod1.text=s
                presenter.loadText(!isUz,s)
            }
        }
    }

    override fun showText(text: String) {
        binding.perevod2.text=text
    }


}