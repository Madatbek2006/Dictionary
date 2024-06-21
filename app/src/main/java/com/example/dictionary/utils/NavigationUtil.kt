package com.example.dictionary.utils
import android.graphics.Color
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.LinearLayout
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.example.dictionary.R
fun ViewGroup.inflate(@LayoutRes r:Int): View {
    return LayoutInflater.from(context).inflate(r,this,false)
}
fun String.myLog()= Log.d("TTT",this)
fun String.onlyLetters() = all { it.isLetter() }
fun String.onlyLettersAndDijid() = all { it.isLetter()||it.isDigit() }

fun FragmentActivity.statusBarTRANSPARENT()=this.apply{
    if (Build.VERSION.SDK_INT >= 19 && Build.VERSION.SDK_INT < 21) {
        setWindowFlag(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, true)
    }
    if (Build.VERSION.SDK_INT >= 19) {
//            SYSTEM_UI_FLAG_LAYOUT_STABLE
        this.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
    }
    if (Build.VERSION.SDK_INT >= 21) {
        setWindowFlag(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false)
        this.window.statusBarColor = Color.TRANSPARENT
    }
    this.window.statusBarColor = Color.TRANSPARENT
}

private fun FragmentActivity.setWindowFlag(bits: Int, on: Boolean) {
    val win = this.window
    val winParams = win.attributes
    if (on) {
        winParams.flags = winParams.flags or bits
    } else {
        winParams.flags = winParams.flags and bits.inv()
    }
    win.attributes = winParams
}

fun FragmentActivity.setStatusBar(view:View):Int{
    val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
    if (resourceId > 0) {
        // Получаем размер статус бара
        val statusBarHeight = resources.getDimensionPixelSize(resourceId)

        // Устанавливаем отступ сверху для корневого элемента макета
        val params = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,  // ширина в пикселях
            statusBarHeight // высота в пикселях
        )
        view.layoutParams = params
        return statusBarHeight
    }
    return 0
}
