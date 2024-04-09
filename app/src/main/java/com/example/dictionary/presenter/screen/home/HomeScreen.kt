package com.example.dictionary.presenter.screen.home

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
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dictionary.R
import com.example.dictionary.databinding.ScreenHomeBinding
import com.example.dictionary.presenter.adapper.CursorAdapter
import com.example.dictionary.presenter.dialog.BottomSheetDialog
import com.example.dictionary.presenter.screen.main.MainController
import com.example.dictionary.presenter.screen.main.MainPresenter

class HomeScreen:Fragment(),HomeController.View {
    private var _binding:ScreenHomeBinding?=null
    private val binding by lazy { _binding!! }
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var drawerToggle:ActionBarDrawerToggle
    private  var isUz=false

    private var isUzb=false
    private val presenter: MainController.Presenter by lazy { MainPresenter(this) }
    private val adapter by lazy { CursorAdapter() }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding=ScreenHomeBinding.inflate(layoutInflater)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        innit()
        innit1()
    }
    private fun innit(){
        drawerLayout = binding.drawerLayout
        drawerToggle = ActionBarDrawerToggle(requireActivity(), drawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawerLayout.addDrawerListener(drawerToggle)
        drawerToggle.syncState()
        drawerLayout.closeDrawers()

        binding.menu.setOnClickListener {
            // Toggle the drawer when the button is clicked
            drawerLayout.openDrawer(GravityCompat.START)
            hideKeyboardFrom(requireActivity(),binding.menu)
        }

        val navigationView = binding.navView
        navigationView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.izbrannoe -> {
                    findNavController().navigate(HomeScreenDirections.actionHomeScreenToBookMarkScreen())

                }
                R.id.dictionary->{
                }
                R.id.speak->{
                    findNavController().navigate(HomeScreenDirections.actionHomeScreenToSpeakScreen())
                }
            }
            binding.drawerLayout.closeDrawer(GravityCompat.START)
            return@setNavigationItemSelectedListener true
        }
        binding.transfer.setOnClickListener {
            if (isUz){
                isUz=false
                setLangvich(isUz)
            }else{
                isUz=true
                setLangvich(isUz)
            }
        }


    }
    fun innit1(){
        presenter.loadFull()
        binding.apply {
            recyclerView.adapter=adapter
            recyclerView.layoutManager= LinearLayoutManager(requireActivity())
            search.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                }

                override fun afterTextChanged(s: Editable?) {
                    if (search.text.isEmpty()){
                        presenter.loadFull()

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
        adapter.onClickItem={it,pos->
//            val dictionary=presenter.getCurrentWord(it)
//            val dialog=DictionaryDialog().apply {
//                arguments= bundleOf(Pair("eng",dictionary.english),Pair("uzb",dictionary.uzbek),Pair("type",dictionary.type),Pair("tran",dictionary.transcript))
//            }
//            dialog.show(requireActivity().supportFragmentManager,"")

//            val bottomShettBinding=BottomSheetDialogFragment(R.layout.bottom_shett)

//            val bottom=BottomShettBinding.inflate(LayoutInflater.from(binding.root.context),binding.root,false)
////            bottomShettBinding.onCreateView(bottom,binding.root,Bundle())
//            bottomShettBinding.show(requireActivity().supportFragmentManager,"")
            val bottom= BottomSheetDialog().apply {
                arguments= bundleOf(Pair("pos",it))
            }
            bottom.show(requireActivity().supportFragmentManager,"")
            bottom.onDestroyListener={

            }

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

    override fun openBookMarkFragment() {
        findNavController().navigate(HomeScreenDirections.actionHomeScreenToBookMarkScreen())
    }
    fun setLangvich(bool:Boolean){
        isUzb=bool
        adapter.setIsUzb(isUzb)
        adapter.notifyDataSetChanged()
        presenter.loadFull()
    }
    fun hideKeyboardFrom(context: Context, view: View?) {
        val imm =
            context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view?.windowToken, 0)
    }

}