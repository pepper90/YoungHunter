package com.jpdevzone.younghunter.finishquiz

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class FinishQuizViewModelFactory(
    private val finishResult: Int,
    private val finishTime: Long,
    private val maxQuestions: Int
) : ViewModelProvider.Factory {

    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FinishQuizViewModel::class.java)) {
            return FinishQuizViewModel(finishResult, finishTime, maxQuestions) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}