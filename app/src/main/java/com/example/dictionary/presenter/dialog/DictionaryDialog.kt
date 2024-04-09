package com.example.dictionary.presenter.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.dictionary.databinding.DialogInfoBinding

class DictionaryDialog:DialogFragment() {
    private var _binding:DialogInfoBinding?=null
    private val binding by lazy { _binding!! }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding=DialogInfoBinding.inflate(layoutInflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var eng1=requireArguments().getString("eng")
        var uzb1=requireArguments().getString("uzb")
        var type1=requireArguments().getString("type")
        var tran1=requireArguments().getString("tran")
        binding.apply {
            binding.ok.setOnClickListener {
                dismiss()
            }
            eng.text=eng1
            uzb.text=uzb1
            type.text=type1
            transcript.text=tran1


        }
    }
    override fun onStart() {
        super.onStart()
        this.dialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

}