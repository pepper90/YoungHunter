package com.jpdevzone.younghunter.dashboard

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.jpdevzone.younghunter.database.QuestionsDatabase
import com.jpdevzone.younghunter.database.QuestionsRepository
import com.jpdevzone.younghunter.database.models.Progress
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class DashboardViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val database = QuestionsDatabase.getInstance(application)
    private val repository = QuestionsRepository(database)

    /**
     * PROGRESS LOADING
     **/

    private val _progressExam = MutableLiveData<Progress?>()
    val progressExam: LiveData<Progress?>
        get() = _progressExam

    private val _progressAnimals = MutableLiveData<Progress?>()
    val progressAnimals: LiveData<Progress?>
        get() = _progressAnimals

    private val _progressLaw = MutableLiveData<Progress?>()
    val progressLaw: LiveData<Progress?>
        get() = _progressLaw

    private val _progressGameManagement = MutableLiveData<Progress?>()
    val progressGameManagement: LiveData<Progress?>
        get() = _progressGameManagement

    private val _progressHuntingMethods = MutableLiveData<Progress?>()
    val progressHuntingMethods: LiveData<Progress?>
        get() = _progressHuntingMethods

    private val _progressGuns = MutableLiveData<Progress?>()
    val progressGuns: LiveData<Progress?>
        get() = _progressGuns

    private val _progressDogs = MutableLiveData<Progress?>()
    val progressDogs: LiveData<Progress?>
        get() = _progressDogs

    private val _progressViruses = MutableLiveData<Progress?>()
    val progressViruses: LiveData<Progress?>
        get() = _progressViruses

    private fun loadProgress() {
        viewModelScope.launch {
            _progressExam.value = repository.loadProgress("exam")
            _progressAnimals.value = repository.loadProgress("animals")
            _progressLaw.value = repository.loadProgress("law")
            _progressGameManagement.value = repository.loadProgress("gameManagement")
            _progressHuntingMethods.value = repository.loadProgress("huntingMethods")
            _progressGuns.value = repository.loadProgress("guns")
            _progressDogs.value = repository.loadProgress("dogs")
            _progressViruses.value = repository.loadProgress("viruses")
        }
    }

    /**
    * DASHBOARD DIALOG
    **/

    private val _showDialog = MutableLiveData<Boolean?>()
    val showDialog: LiveData<Boolean?>
        get() = _showDialog

    fun showDialog() {
        _showDialog.value = true
    }

    fun resetDialog() {
        _showDialog.value = null
    }

    /**
     * NAVIGATION
     **/

    private val _navigateToQuiz = MutableLiveData<Boolean?>()
    val navigateToQuiz: LiveData<Boolean?>
        get() = _navigateToQuiz

    fun navigateToQuiz() {
        _navigateToQuiz.value = true
    }

    fun onQuizNavigationComplete() {
        _navigateToQuiz.value = null
    }

    fun navigation(progress: Progress) {
        when (progress) {
            null -> navigateToQuiz()
            else -> showDialog()
        }
    }

    init {
        loadProgress()
    }
}