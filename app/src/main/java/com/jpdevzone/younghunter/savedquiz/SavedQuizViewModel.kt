package com.jpdevzone.younghunter.savedquiz

import android.app.Application
import android.os.CountDownTimer
import android.text.format.DateUtils
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.jpdevzone.younghunter.database.QuestionsDatabase
import com.jpdevzone.younghunter.database.QuestionsRepository
import com.jpdevzone.younghunter.database.models.Progress
import com.jpdevzone.younghunter.database.models.Question
import kotlinx.coroutines.launch

class SavedQuizViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val database = QuestionsDatabase.getInstance(application)
    private val repository = QuestionsRepository(database)

    /**
     * SAVED QUESTION
     **/

    // Holds a list of question ids
    private val _range = MutableLiveData<List<Int>?>()
    val range: LiveData<List<Int>?>
        get() = _range

    // Holds current question & options
    private val _question = MutableLiveData<Question?>()
    val question: LiveData<Question?>
        get() = _question

    // Gets question from repository
    fun setupEnvironment(topic: String) {
        viewModelScope.launch {
            _range.value = repository.loadProgress(topic).range
            _position.value = repository.loadProgress(topic).position
            savedTime.value = repository.loadProgress(topic).time
            _totalAnswers.value = repository.loadProgress(topic).correctAnswers
            setTimer(topic)
        }
        setProgressBarMax(topic)
    }

    // Loads question based on range
    private fun loadQuestion(id: Int) {
        viewModelScope.launch {
            _question.value = repository.getQuestion(id)
        }
    }

    fun loadOrFinish(position: Int) {
        if (position <= range.value?.size!!) {
            loadQuestion(range.value!![position.minus(1)])
        } else {
            navigateToFinish()
        }
    }

    /**
     * SAVED POSITION
     **/

    // Holds current question position & progress position
    private val _position = MutableLiveData<Int?>()
    val position: LiveData<Int?>
        get() = _position

    // Position increment
    fun next() {
        resetAllOption()
        _position.value = _position.value?.plus(1)
    }

    /**
     * TIMER & PROGRESS BAR
     **/

    // Holds saved time value from Db
    private val savedTime = MutableLiveData<Long?>()

    // Holds current time value
    private val _currentTime = MutableLiveData<Long?>()
    val currentTime: LiveData<Long?>
        get() = _currentTime

    // Holds elapsed time value used in FinishQuizFragment
    private val _elapsedTime = MutableLiveData<Long?>()
    val elapsedTime: LiveData<Long?>
        get() = _elapsedTime

    // Holds current time value
    private val _progressBarMax = MutableLiveData<Int?>()
    val progressBarMax: LiveData<Int?>
        get() = _progressBarMax

    // Default timer values
    companion object {
        private const val DONE = 0L
        private const val ONE_SECOND = 1000L
        private const val COUNTDOWN_TIME_EXAM = 5401000L
        private const val COUNTDOWN_TIME_MINITEST = 1501000L
        private var MAX_TIME: Long? = 0
        private var COUNTDOWN_TIME: Long? = 0
        private var TIME_LEFT: Long = 0
        private lateinit var timer: CountDownTimer
    }

    // Setting timer depending ot topic string from safeArgs
    private fun setTimer(topic: String) {
        MAX_TIME = when (topic) {
            "exam" -> COUNTDOWN_TIME_EXAM
            else -> COUNTDOWN_TIME_MINITEST
        }
        COUNTDOWN_TIME = savedTime.value
        COUNTDOWN_TIME?.let { timer(it) }
        Log.d("TimerTesting", "Timer has started!")
    }

    // Timer function
    private fun timer(timeLeft: Long) {
        timer = object : CountDownTimer(timeLeft, ONE_SECOND) {

            override fun onTick(millisUntilFinished: Long) {
                TIME_LEFT = millisUntilFinished
                _currentTime.value = (TIME_LEFT / ONE_SECOND)
                _elapsedTime.value = (MAX_TIME?.minus(millisUntilFinished))?.div(ONE_SECOND)
            }

            override fun onFinish() {
                _currentTime.value = DONE
                _navigateToFinish.value = true
            }
        }
        timer.start()
    }

    // Pause timer to avoid memory leaks. Also called in onPause.
    fun pauseTimer() {
        timer.cancel()
        Log.d("TimerTesting", "Timer has been cancelled! ${formattedTime(_currentTime.value!!)}")
    }

    // Resume timer when screen is in focus. Also called in onResume.
    // Prevents memory leaks.
    fun resumeTimer() {
        if (_currentTime.value != null) {
            timer(TIME_LEFT)
            Log.d("TimerTesting", "Timer has been resumed! ${formattedTime(_currentTime.value!!)}")
        }
    }

    // Format time into readable string
    fun formattedTime(time: Long) : String {
        return DateUtils.formatElapsedTime(time)
    }

    // Sets progress bar max depending ot topic string from safeArgs
    private fun setProgressBarMax(topic: String) {
        when (topic) {
            "exam" -> _progressBarMax.value = 104
            else -> _progressBarMax.value = 30
        }
    }

    /**
     * OPTION STATES & INDEXES (clicked or not)
     **/

    // Hold value fro Option One
    private val _optionOneState = MutableLiveData<Boolean?>()
    val optionOneState: LiveData<Boolean?>
        get() = _optionOneState

    // Holds Option One index
    private val _optionOneIndex = MutableLiveData(1)
    val optionOneIndex: LiveData<Int>
        get() = _optionOneIndex

    // Sets Option One value to true. Resets other options.
    //Used for Binding adapter to change button background, stroke & text colors
    fun onOptionOneSelected() {
        _selectedOptionIndex.value = _optionOneIndex.value
        _optionTwoState.value = false
        _optionThreeState.value = false
        _optionOneState.value = true
    }

    // Hold value fro Option Two
    private val _optionTwoState = MutableLiveData<Boolean?>()
    val optionTwoState: LiveData<Boolean?>
        get() = _optionTwoState

    // Hold Option Two index
    private val _optionTwoIndex = MutableLiveData(2)
    val optionTwoIndex: LiveData<Int>
        get() = _optionTwoIndex

    // Sets Option Two value to true. Resets other options.
    //Used for Binding adapter to change button background, stroke & text colors
    fun onOptionTwoSelected() {
        _selectedOptionIndex.value = _optionTwoIndex.value
        _optionOneState.value = false
        _optionThreeState.value = false
        _optionTwoState.value = true
    }

    // Hold value fro Option Three
    private val _optionThreeState = MutableLiveData<Boolean?>()
    val optionThreeState: LiveData<Boolean?>
        get() = _optionThreeState

    // Hold Option Three index
    private val _optionThreeIndex = MutableLiveData(3)
    val optionThreeIndex: LiveData<Int>
        get() = _optionThreeIndex

    // Sets Option Three value to true. Resets other options
    // Used for Binding adapter to change button background, stroke & text colors
    fun onOptionThreeSelected() {
        _selectedOptionIndex.value = _optionThreeIndex.value
        _optionOneState.value = false
        _optionTwoState.value = false
        _optionThreeState.value = true
    }

    // Resets all options back to normal when Next btn is clicked
    // Makes selectedOptionIndex null
    private fun resetAllOption() {
        _correctAnswer.value = null
        _selectedOptionIndex.value = null
        _optionOneState.value = false
        _optionTwoState.value = false
        _optionThreeState.value = false
    }

    /**
     * CHECK ANSWER LOGIC
     **/

    // Holds the correctAnswer value from _question.value.correctAnswer
    private val _correctAnswer = MutableLiveData<Int?>()
    val correctAnswer: LiveData<Int?>
        get() = _correctAnswer

    // Get the index from the selected option
    // Used in Binding adapter to mart correct and wrong answers
    private val _selectedOptionIndex = MutableLiveData<Int?>()
    val selectedOptionIndex: LiveData<Int?>
        get() = _selectedOptionIndex

    // Holds the total number of correct answers
    private val _totalAnswers = MutableLiveData<Int?>()
    val totalAnswers: LiveData<Int?>
        get() = _totalAnswers

    // Checks if the selected answer is correct
    // If true, increases the total correct answers by 1
    fun checkAnswer() {
        _correctAnswer.value = _question.value?.correctAnswer

        if (_selectedOptionIndex.value == _correctAnswer.value) {
            _totalAnswers.value = totalAnswers.value?.plus(1)
            Log.d("Correct answers", totalAnswers.value.toString())
        }
    }

    /**
     * PROGRESS SAVING & CLEARING
     **/

    fun saveProgress(topic: String) {
        val progress = Progress(
            topic,
            range.value,
            _position.value,
            TIME_LEFT,
            _totalAnswers.value
        )
        viewModelScope.launch {
            repository.insertProgress(progress)
        }
    }

    fun clearProgress(topic: String) {
        viewModelScope.launch {
            repository.deleteProgress(topic)
        }
    }

    /**
     * NAVIGATION
     **/

    // Holds value for navigating to FinishQuiz
    // If true, navigation is triggered
    private val _navigateToFinish = MutableLiveData<Boolean?>()
    val navigateToFinish: LiveData<Boolean?>
        get() = _navigateToFinish

    // Sets value to true & triggers navigation
    fun navigateToFinish() {
        _navigateToFinish.value = true
    }

    // Resets value to null
    fun doneNavigating() {
        _navigateToFinish.value = null
    }
}