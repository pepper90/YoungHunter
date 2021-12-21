package com.jpdevzone.younghunter.quizquestion

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jpdevzone.younghunter.database.QuestionsDatabaseDao

class QuizQuestionViewModelFactory(
    private val dataSource: QuestionsDatabaseDao,
    private val application: Application
) : ViewModelProvider.Factory {

    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(QuizQuestionViewModel::class.java)) {
            return QuizQuestionViewModel(dataSource, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}