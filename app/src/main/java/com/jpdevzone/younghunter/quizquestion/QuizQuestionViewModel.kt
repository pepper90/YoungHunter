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

    /**
    * QUESTION
    * */

    // Holds current question & options
    private val _question = MutableLiveData<Question>()
    val question: LiveData<Question>
        get() = _question

    // Gets question from repository
    fun getQuestion(id: Int) {
        viewModelScope.launch {
            _question.value = repository.getQuestion(id)
        }
    }

    /**
    * POSITION
    * */

    // Holds current question position & progress position
    private val _position = MutableLiveData(1)
    val position: LiveData<Int>
        get() = _position

    init {
        _position.value = 1
    }

    // Position increment
    fun next() {
        resetAllOption()
        _position.value = _position.value?.plus(1)
    }

    /**
    * TIMER & PROGRESS BAR
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

    /**
    * OPTION STATES & INDEXES (clicked or not)
    * */

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
    fun resetAllOption() {
        _correctAnswer.value = null
        _selectedOptionIndex.value = null
        _optionOneState.value = false
        _optionTwoState.value = false
        _optionThreeState.value = false
    }

    /**
    * CHECK ANSWER LOGIC
    * */

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
    private val _totalAnswers = MutableLiveData(0)
    val totalAnswers: LiveData<Int>
        get() = _totalAnswers

    fun checkAnswer() {
        _correctAnswer.value = _question.value?.correctAnswer

        if (_selectedOptionIndex.value == _correctAnswer.value) {
            _totalAnswers.value = totalAnswers.value?.plus(1)
            Log.d("Correct answers", totalAnswers.value.toString())
        }
    }

    /**
     * NAVIGATION
     * */
}
