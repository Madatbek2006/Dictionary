package com.example.dictionary.presenter.screen.main

import android.app.Activity
import android.content.Context
import android.database.Cursor
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.os.bundleOf
import androidx.core.view.GravityCompat
import androidx.core.view.isVisible
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dictionary.R
import com.example.dictionary.databinding.BottomShettBinding
import com.example.dictionary.presenter.adapper.CursorAdapter
import com.example.dictionary.databinding.ScreenMainBinding
import com.example.dictionary.presenter.activity.MainActivity
import com.example.dictionary.presenter.dialog.BottomSheetDialog

class MainScreen:Fragment(), MainController.View {
    var _binding:ScreenMainBinding?=null
    private val binding by lazy { _binding!! }
    private val presenter: MainController.Presenter by lazy { MainPresenter(this) }
    private val adapter by lazy { CursorAdapter() }
    var onClickFavourite:(()->Unit)?=null

    var isUzb=false
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding=ScreenMainBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        innit()
    }
    fun innit(){
        presenter.loadFull(isUzb)
        binding.apply {
            recyclerView.adapter=adapter
            recyclerView.layoutManager=LinearLayoutManager(requireActivity())
            search.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                }

                override fun afterTextChanged(s: Editable?) {
                    if (search.text.isEmpty()){
                        presenter.loadFull(isUzb)

                    }else{
                        val text=search.text.toString()
                        presenter.loadDictionary(text,isUzb)
                    }

                }
            })
            searchButton.setOnClickListener {
                hideKeyboardFrom(requireActivity(),searchButton)
            }



        }
        adapter.onClickFavourite={
            onClickFavourite?.invoke()
        }
        adapter.onClickItem={it,pos->
            val bottom=BottomSheetDialog().apply {
                arguments= bundleOf(Pair("pos",it))
            }
            bottom.show(requireActivity().supportFragmentManager,"")
            bottom.onDestroyListener={

            }
        }

        binding.speak.setOnClickListener{
            if (isUzb){
                (requireActivity() as MainActivity).promptSpeechInput("uz-Uz")
            }else{

                (requireActivity() as MainActivity).promptSpeechInput("eng-Eng")
            }
        }

        (requireActivity() as MainActivity).shareData={
            binding.search.setText(it.substring(1,it.length-1))
        }
    }

    override fun showDictionary(cursor: Cursor) {
        if (cursor.count==0){
            binding.notFound.visibility=View.VISIBLE
        }else{
            binding.notFound.visibility=View.INVISIBLE
        }

        adapter.setCursor(cursor,binding.search.text.toString())
    }


    fun setLangvich(bool:Boolean){
        isUzb=bool
        adapter.setIsUzb(isUzb)
        binding.search.setText("")
        adapter.notifyDataSetChanged()
        presenter.loadFull(isUzb)
    }
    fun hideKeyboardFrom(context: Context, view: View?) {
        val imm =
            context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view?.windowToken, 0)
    }

    override fun onDestroy() {
        _binding=null
        super.onDestroy()
    }

    fun setTranswer(){
        binding.search.setText("")
        if (isUzb){
            isUzb=false
            setLangvich(isUzb)
        }else{
            isUzb=true
            setLangvich(true)
        }
    }
}
