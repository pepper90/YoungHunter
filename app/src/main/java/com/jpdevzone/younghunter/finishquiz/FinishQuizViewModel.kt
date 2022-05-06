package com.jpdevzone.younghunter.finishquiz

import android.text.format.DateUtils
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel

class FinishQuizViewModel(
    finishResult: Int,
    finishTime: Long,
    maxQuestions: Int
) : ViewModel() {

    private val _finalResult = MediatorLiveData<Int>()
    val finalResult: LiveData<Int>
        get() = _finalResult


    private val _finalTime = MediatorLiveData<Long>()
    val finalTime: LiveData<Long>
        get() = _finalTime

    private val _maxQuestions = MediatorLiveData<Int>()
    val maxQuestions: LiveData<Int>
        get() = _maxQuestions

//    private val _limit = MediatorLiveData<Int>()
//    val limit: LiveData<Int>
//        get() = _limit

    init {
        _finalResult.value = finishResult
        _finalTime.value = finishTime
        _maxQuestions.value = maxQuestions
    }

    fun formattedTime(time: Long) : String {
        return DateUtils.formatElapsedTime(time)
    }

    fun passOrFail() : Boolean {
        return when {
            _finalResult.value!! >= (_maxQuestions.value!! / 1.3).toInt() -> true
            else -> false
        }
    }
}