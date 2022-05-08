package com.jpdevzone.younghunter.utils

import android.graphics.Color
import com.google.android.material.button.MaterialButton
import com.jpdevzone.younghunter.R

/**
* Backgrounds
**/

val backgrounds = arrayOf(
    R.drawable.backimg_one,
    R.drawable.backimg_two,
    R.drawable.backimg_three,
    R.drawable.backimg_four,
    R.drawable.backimg_five,
    R.drawable.backimg_six,
    R.drawable.backimg_seven,
    R.drawable.backimg_eight,
    R.drawable.backimg_nine,
    R.drawable.backimg_ten)

val setBackground = backgrounds.random()

/**
* View Colorizer for options
**/

fun colorizeAnswer (view: MaterialButton, answer: Int, color: String) {
    when(answer){
        1 -> view.setBackgroundColor(Color.parseColor(color))
        2 -> view.setBackgroundColor(Color.parseColor(color))
        3 -> view.setBackgroundColor(Color.parseColor(color))
    }
}

/**
* String builder for copy & share buttons
**/

fun stringBuilder(message: String, result: String) : CharSequence {
    val data = StringBuilder()
    data.append(message)
    data.append("\n\n")
    data.append(result)
    data.append("\n\nМлад ловец - тестове за решаване и самоподготовка / Google Play: https://play.google.com/store/apps/details?id=com.jpdevzone.younghunter")
    return data
}