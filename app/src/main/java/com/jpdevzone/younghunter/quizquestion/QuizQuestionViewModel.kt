package com.jpdevzone.younghunter.quizquestion

import android.app.Application
import android.os.CountDownTimer
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.jpdevzone.younghunter.database.Question
import com.jpdevzone.younghunter.database.QuestionsDatabase
import com.jpdevzone.younghunter.database.QuestionsRepository
import kotlinx.coroutines.launch
import kotlin.properties.Delegates

class QuizQuestionViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val database = QuestionsDatabase.getInstance(application)
    private val repository = QuestionsRepository(database)

    /*
    * Question
    * */



    /*
    * Position
    * */

    // Holds current question position & progress position
    private val _position = MutableLiveData<Int>()
    val position: LiveData<Int>
    get() = _position

    init {
        _position.value = 1
    }

    fun next() {
        _position.value = _position.value?.plus(1)
    }

    /*
    * Timer and Progress bar
    * */

    // Holds current time value
    private val _currentTime = MutableLiveData<Long>()
    val currentTime: LiveData<Long>
        get() = _currentTime

    // Default timer values
    companion object {
        private const val DONE = 0L
        private const val ONE_SECOND = 1000L
        private const val COUNTDOWN_TIME_EXAM = 5400000L
        private const val COUNTDOWN_TIME_MINITEST = 1500000L
        private var COUNTDOWN_TIME by Delegates.notNull<Long>()
    }

    // Setting timer depending ot topic string from safeArgs
    fun timer(topic: String) {
        val timer: CountDownTimer
        COUNTDOWN_TIME = when (topic) {
            "exam" -> COUNTDOWN_TIME_EXAM
            else -> COUNTDOWN_TIME_MINITEST
        }
        timer = object : CountDownTimer(COUNTDOWN_TIME, ONE_SECOND) {

            override fun onTick(millisUntilFinished: Long) {
                _currentTime.value = (millisUntilFinished / ONE_SECOND)
            }

            override fun onFinish() {
                _currentTime.value = DONE
            }
        }

        timer.start()
    }

    // Sets progress bar max depending ot topic string from safeArgs
    fun setProgressBarMax(topic: String) : Int {
        return when (topic) {
            "exam" -> 104
            else -> 30
        }
    }

}
