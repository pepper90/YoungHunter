package com.jpdevzone.younghunter

import android.widget.ImageView
import androidx.databinding.BindingAdapter

@BindingAdapter("setIcon")
fun setIcon(view: ImageView, icon: Int) {
    view.setImageResource(icon)
}