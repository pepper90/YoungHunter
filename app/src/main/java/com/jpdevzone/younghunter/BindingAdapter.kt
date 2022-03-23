package com.jpdevzone.younghunter

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.Typeface
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.google.android.material.button.MaterialButton
import com.jpdevzone.younghunter.quizquestion.QuizQuestionFragmentDirections

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

private fun colorizeAnswer (view: MaterialButton, answer: Int, color: String) {
    when(answer){
        1 -> view.setBackgroundColor(Color.parseColor(color))
        2 -> view.setBackgroundColor(Color.parseColor(color))
        3 -> view.setBackgroundColor(Color.parseColor(color))
    }
}

@BindingAdapter("correctAnswer", "optionIndex", "optionState", requireAll = true)
fun checkAnswer(view: MaterialButton, correctAnswer: Int?, index: Int?, state: Boolean?) {
    if (correctAnswer != null && index != null) {
        when {
            correctAnswer != index && state == true -> colorizeAnswer(view, correctAnswer,"#ea4647")
            correctAnswer == index && state == true -> colorizeAnswer(view, index,"#99cc00")
        }
    }
}

@BindingAdapter("setButtonState")
fun setButtonState(view: MaterialButton, optionIndex: Int?) {
    when (optionIndex in 1..3) {
        true -> view.isClickable = true
        false -> view.isClickable = false
    }
}

