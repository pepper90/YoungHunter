package com.jpdevzone.younghunter.dashboard

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.jpdevzone.younghunter.database.QuestionsDatabase
import com.jpdevzone.younghunter.database.QuestionsRepository
import com.jpdevzone.younghunter.database.models.Progress
import kotlinx.coroutines.launch
import java.util.*

class DashboardViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val database = QuestionsDatabase.getInstance(application)
    private val repository = QuestionsRepository(database)

    /**
     * PROGRESS LOADING
     **/

    // These variables hold progress values by category from Db
    private val _progressExam = MutableLiveData<Progress?>()
    private val _progressAnimals = MutableLiveData<Progress?>()
    private val _progressLaw = MutableLiveData<Progress?>()
    private val _progressGameManagement = MutableLiveData<Progress?>()
    private val _progressHuntingMethods = MutableLiveData<Progress?>()
    private val _progressGuns = MutableLiveData<Progress?>()
    private val _progressDogs = MutableLiveData<Progress?>()
    private val _progressViruses = MutableLiveData<Progress?>()

    // Loads progress values by category from Db
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

    // Holds progress value which is in focus
    private val _progress = MutableLiveData<Progress?>()
    val progress: LiveData<Progress?>
        get() = _progress

    // These functions give swap values of in-focus progress
    fun progressExam() {
        _progress.value = _progressExam.value
    }

    fun progressAnimals() {
        _progress.value = _progressAnimals.value
    }

    fun progressLaw() {
        _progress.value = _progressLaw.value
    }

    fun progressGameManagement() {
        _progress.value = _progressGameManagement.value
    }

    fun progressHuntingMethods() {
        _progress.value = _progressHuntingMethods.value
    }

    fun progressGuns() {
        _progress.value = _progressGuns.value
    }

    fun progressDogs() {
        _progress.value = _progressDogs.value
    }

    fun progressViruses() {
        _progress.value = _progressViruses.value
    }

    // Clear in-focus progress value
    fun clearProgressValue() {
        _progress.value = null
    }

    // Clears progress form Db
    fun clearProgressFromDb(topic: String) {
        viewModelScope.launch {
            repository.deleteProgress(topic)
        }
    }

    /**
     * TIME FORMATTING FOR DASHBOARD POPUP WINDOW
     **/

    // Format time into readable string
    fun toTime(timer: Long) : String {
        val minutes: Int = ((timer / 1000) / 60).toInt()
        val seconds: Int = ((timer / 1000) % 60).toInt()
        return (String.format(Locale.getDefault(),"%02d:%02d",minutes,seconds))
    }

    init {
        loadProgress()
    }
}