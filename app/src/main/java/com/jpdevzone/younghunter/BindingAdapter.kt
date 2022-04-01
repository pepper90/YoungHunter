package com.jpdevzone.younghunter

import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.Typeface
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.google.android.material.button.MaterialButton
import com.jpdevzone.younghunter.utils.colorizeAnswer

@BindingAdapter("setIcon")
fun setIcon(view: ImageView, icon: Int) {
    view.setImageResource(icon)
}

@BindingAdapter("setOptionState")
fun setOptionState(view: MaterialButton, state: Boolean) {
    when (state) {
        true -> {
            view.setBackgroundColor(Color.parseColor("#e4e65e"))
            view.strokeColor = ColorStateList.valueOf(Color.parseColor("#9e9d24"))
            view.setTextColor(Color.parseColor("#424242"))
            view.setTypeface(view.typeface, Typeface.BOLD)
        }
        false -> {
            view.setBackgroundColor(Color.parseColor("#9e9d24"))
            view.strokeColor = ColorStateList.valueOf(Color.parseColor("#FFFFFF"))
            view.setTextColor(Color.parseColor("#FFFFFF"))
            view.typeface = Typeface.DEFAULT
        }
    }
}

@BindingAdapter("correctAnswer", "optionIndex", "selectedOptionIndex", requireAll = true)
fun checkAnswer(view: MaterialButton, correctAnswer: Int?, optionIndex: Int?, selectedOptionIndex: Int?) {
    if (correctAnswer != null && selectedOptionIndex != null) {
        when {
            correctAnswer != selectedOptionIndex && selectedOptionIndex == optionIndex -> {
                colorizeAnswer(view, selectedOptionIndex,"#ea4647")
            }
            correctAnswer == optionIndex -> colorizeAnswer(view, optionIndex,"#99cc00")
        }
    }
}

