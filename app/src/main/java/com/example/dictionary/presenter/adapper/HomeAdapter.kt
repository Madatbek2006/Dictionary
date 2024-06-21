package com.example.dictionary.presenter.adapper

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.dictionary.presenter.screen.bookmark.BookMarkScreen
import com.example.dictionary.presenter.screen.main.MainScreen
import com.example.dictionary.presenter.screen.speak.AddWordScreen

class HomeAdapter(fm:Fragment,
    private val mainScreen: MainScreen,
    private val bookMarkScreen: BookMarkScreen,
    private val addWordScreen: AddWordScreen):FragmentStateAdapter(fm) {
    override fun getItemCount(): Int =3

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0->mainScreen
            1->bookMarkScreen
            else->addWordScreen
        }
    }
}