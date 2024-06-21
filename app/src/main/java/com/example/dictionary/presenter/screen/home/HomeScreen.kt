package com.example.dictionary.presenter.screen.home

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.get
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.airbnb.lottie.LottieAnimationView
import com.airbnb.lottie.LottieProperty
import com.airbnb.lottie.model.KeyPath
import com.example.dictionary.R
import com.example.dictionary.databinding.ScreenHomeBinding
import com.example.dictionary.presenter.adapper.HomeAdapter
import com.example.dictionary.presenter.screen.bookmark.BookMarkScreen
import com.example.dictionary.presenter.screen.main.MainScreen
import com.example.dictionary.presenter.screen.speak.AddWordScreen
import com.example.dictionary.utils.myLog
import com.example.dictionary.utils.setStatusBar
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class HomeScreen:Fragment(),HomeController.View {
    private var _binding:ScreenHomeBinding?=null
    private val binding by lazy { _binding!! }
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var drawerToggle:ActionBarDrawerToggle
    private  var isUz=false
    private var isActiveDrawerLayout=false
    private var isUzb=false
    private lateinit var adapter:HomeAdapter
    private  var mainScreen: MainScreen=MainScreen()
    private  var bookMarkScreen=BookMarkScreen()
    private  var addWordScreen=AddWordScreen()
    private val presenter:HomeController.Presenter by lazy {  HomePresenter(this)}
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
        initAdapter()
        innit()
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                "handleOnBackPressed".myLog()
                if (binding.viewPager2.currentItem!=0){
                    setPos(0)
                }else{
                    requireActivity().finish()
                }
            }

        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)

//        val animationView: LottieAnimationView = findViewById(R.id.animation_view)
        binding.transfer.addValueCallback(
            KeyPath("**"),
            LottieProperty.COLOR_FILTER
        ) { PorterDuffColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP) }
    }
    private fun innit()=binding.apply{
        requireActivity().setStatusBar(spase)

        menu.setOnClickListener {
            // Toggle the drawer when the button is clicked
            drawerLayout.openDrawer(Gravity.RIGHT)
            hideKeyboardFrom(requireActivity(),binding.menu)

        }
        navView.post {
            if (!isActiveDrawerLayout){
                requireActivity().setStatusBar(navView.findViewById(R.id.spase))
                isActiveDrawerLayout=true
            }
        }


        navView.setNavigationItemSelectedListener {
            when (it.itemId) {
                com.example.dictionary.R.id.home->{
                    setPos(0)
                }
                com.example.dictionary.R.id.favourites -> {
                    setPos(1)
                }
                com.example.dictionary.R.id.speak->{
                    setPos(2)
                }
            }
            binding.drawerLayout.closeDrawer(Gravity.RIGHT)
            return@setNavigationItemSelectedListener true
        }
        for (i in 0 until 3){
            bottomNavigationView.menu[i].setOnMenuItemClickListener {
                setPos(i)
                return@setOnMenuItemClickListener true
            }
        }
        back.setOnClickListener {
            if (binding.viewPager2.currentItem!=0){
                setPos(0)
            }else{
                findNavController().navigateUp()
            }
        }
        transfer.setOnClickListener {
            transfer.playAnimation()
            if (mainScreen._binding!=null){
                mainScreen.setTranswer()
            }
        }
    }

    override fun openBookMarkFragment() {
        findNavController().navigate(HomeScreenDirections.actionHomeScreenToBookMarkScreen())
    }

    private fun initAdapter() {
        adapter= HomeAdapter(this,mainScreen,bookMarkScreen, addWordScreen)
        binding.apply {
            viewPager2.isUserInputEnabled=false
            viewPager2.adapter=adapter
        }


        mainScreen.onClickFavourite={
            if (bookMarkScreen._binding!=null){
                lifecycleScope.launch {
                    delay(100)
                    bookMarkScreen.showDictionary(presenter.getCursor())
                }
            }
        }
        bookMarkScreen.onClickFavourite={
            if (mainScreen._binding!=null){
                "salom".myLog()
                lifecycleScope.launch {
                    delay(100)
                    mainScreen.setLangvich(mainScreen.isUzb)
                }
            }
        }
        addWordScreen.setOnNewWordListener={
            if (mainScreen._binding!=null){
                "salom".myLog()
                lifecycleScope.launch {
                    delay(100)
                    mainScreen.setLangvich(mainScreen.isUzb)
                    Toast.makeText(requireContext(),"Word added successfully", Toast.LENGTH_SHORT).show()
                }
            }
            if (bookMarkScreen._binding!=null){
                lifecycleScope.launch {
                    delay(100)
                    bookMarkScreen.showDictionary(presenter.getCursor())
                }
            }

        }

    }

    fun hideKeyboardFrom(context: Context, view: View?) {
        val imm =
            context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view?.windowToken, 0)
    }
    fun setPos(pos:Int){
        binding.apply {
            if (pos==0){
                back.visibility=View.GONE
                transfer.visibility=View.VISIBLE
                screenTxt.text="Home"
//                if (viewPager2.currentItem!=0){
//                    if (
//                        mainScreen._binding!=null
//                    ){
//                        mainScreen.setLangvich(mainScreen.isUzb)
//                    }
//                }
            }else if (pos==1){
                back.visibility=View.VISIBLE
                transfer.visibility=View.GONE
//                if (viewPager2.currentItem!=1){
//                    if (
//                        bookMarkScreen._binding!=null
//                    ){
//                        bookMarkScreen.showDictionary(presenter.getCursor())
//                    }
//                }
                screenTxt.text="Favourites"
            }else if (pos==2){

                back.visibility=View.VISIBLE
                transfer.visibility=View.GONE
                screenTxt.text="Add Word"
            }
            viewPager2.currentItem=pos
            bottomNavigationView.menu[pos].isChecked=true
        }
    }

}