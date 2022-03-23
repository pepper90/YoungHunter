package com.jpdevzone.younghunter

import android.graphics.Color
import com.google.android.material.button.MaterialButton

/*
* Backgrounds
* */

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

fun colorizeAnswer (view: MaterialButton, answer: Int, color: String) {
    when(answer){
        1 -> view.setBackgroundColor(Color.parseColor(color))
        2 -> view.setBackgroundColor(Color.parseColor(color))
        3 -> view.setBackgroundColor(Color.parseColor(color))
    }
}