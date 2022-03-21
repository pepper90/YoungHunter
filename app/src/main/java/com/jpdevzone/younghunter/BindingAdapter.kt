package com.jpdevzone.younghunter

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.Typeface
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.google.android.material.button.MaterialButton

@BindingAdapter("setIcon")
fun setIcon(view: ImageView, icon: Int) {
    view.setImageResource(icon)
}

@BindingAdapter("setOptionState")
fun setOptionState(view: MaterialButton, state: Boolean) {
    when (state) {
        false -> {
            view.setBackgroundColor(Color.parseColor("#9e9d24"))
            view.strokeColor = ColorStateList.valueOf(Color.parseColor("#FFFFFF"))
            view.setTextColor(Color.parseColor("#FFFFFF"))
            view.typeface = Typeface.DEFAULT
        }
        else -> {
            view.setBackgroundColor(Color.parseColor("#e4e65e"))
            view.strokeColor = ColorStateList.valueOf(Color.parseColor("#9e9d24"))
            view.setTextColor(Color.parseColor("#424242"))
            view.setTypeface(view.typeface, Typeface.BOLD)
        }
    }
}

@BindingAdapter("setNextState")
fun setNextState(view: MaterialButton, optionSelected: Boolean) {

}