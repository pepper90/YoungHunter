package com.jpdevzone.younghunter.quizquestion

import android.app.Application
import android.os.CountDownTimer
import androidx.lifecycle.*
import com.jpdevzone.younghunter.database.Question
import com.jpdevzone.younghunter.database.QuestionsDatabaseDao
import kotlin.properties.Delegates


class QuizQuestionViewModel(
    val database: QuestionsDatabaseDao,
    application: Application
) : AndroidViewModel(application) {

    companion object {
        private const val DONE = 0L
        private const val ONE_SECOND = 1000L
        private const val COUNTDOWN_TIME_EXAM = 5400000L
        private const val COUNTDOWN_TIME_MINITEST = 1500000L
        private var COUNTDOWN_TIME by Delegates.notNull<Long>()
    }

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

    private val _currentTime = MutableLiveData<Long>()
    val currentTime: LiveData<Long>
        get() = _currentTime

    private var _questionsList = MediatorLiveData<List<Question>>()
    val questionsList: LiveData<List<Question>>
        get() = _questionsList

    fun getQuestions(topic: String) {
        _questionsList
            .addSource(
                database.getQuestions(topic),
                _questionsList::setValue
            )
    }

    private val _position = MutableLiveData<Int>()
    val position: LiveData<Int>
        get() = _position

    private val _correctAnswers = MutableLiveData<Int>()
    val correctAnswers: LiveData<Int>
        get() = _correctAnswers


    init {
        _position.value = 1
        _correctAnswers.value = 0
    }

    fun onNext() {
        _position.value = position.value?.plus(1)
    }

    fun onCorrect() {
        _correctAnswers.value = correctAnswers.value?.minus(1)
    }

    fun setProgressBarMax(topic: String) : Int {
        return when (topic) {
            "exam" -> 104
            else -> 30
        }
    }

}


//    private var viewModelJob = Job()
//    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

//    override fun onCleared() {
//        super.onCleared()
//        viewModelJob.cancel()
//    }




//    fun test() {
//        uiScope.launch {
//
//
//        }
//    }

//    private suspend fun testTest() {
//        withContext(Dispatchers.IO) {
//        }
//    }
