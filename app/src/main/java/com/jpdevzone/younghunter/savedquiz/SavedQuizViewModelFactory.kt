package com.jpdevzone.younghunter.savedquiz

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class SavedQuizViewModelFactory(
    private val application: Application,
) : ViewModelProvider.Factory {

    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SavedQuizViewModel::class.java)) {
            return SavedQuizViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}