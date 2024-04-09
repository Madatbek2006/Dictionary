package com.example.dictionary.utils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.example.dictionary.R

fun FragmentActivity.addScreen(fm : Fragment) {
    supportFragmentManager.beginTransaction()
        .add(R.id.container, fm)
        .commit()
}

fun FragmentActivity.replaceScreen(fm : Fragment) {
    supportFragmentManager.beginTransaction()
        .replace(R.id.container, fm)
        .addToBackStack(fm::class.java.name)
        .commit()
}

fun FragmentActivity.replaceScreenWithoutSave(fm : Fragment) {
    supportFragmentManager.beginTransaction()
        .replace(R.id.container, fm)
        .commit()
}


fun FragmentActivity.popBackStack() {
    supportFragmentManager.popBackStack()
}

fun Fragment.replaceScreen(fm : Fragment) {
    requireActivity().replaceScreen(fm)
}

fun Fragment.replaceScreenWithoutSave(fm : Fragment) {
    requireActivity().replaceScreenWithoutSave(fm)
}

fun Fragment.popBackStack() {
    requireActivity().popBackStack()
}
fun ViewGroup.inflate(@LayoutRes r:Int): View {
    return LayoutInflater.from(context).inflate(r,this,false)
}
fun String.myLog()= Log.d("TTT",this)
fun String.onlyLetters() = all { it.isLetter() }
fun String.onlyLettersAndDijid() = all { it.isLetter()||it.isDigit() }