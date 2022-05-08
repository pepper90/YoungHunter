package com.jpdevzone.younghunter.database

import com.jpdevzone.younghunter.database.models.Progress
import com.jpdevzone.younghunter.database.models.Question

class QuestionsRepository(
    private val database: QuestionsDatabase
) {

    /**
     * QUESTIONS METHODS
     **/

    suspend fun getQuestion(id: Int): Question {
        return database.questionsDatabaseDao.get(id)
    }

    /**
     * PROGRESS METHODS
     **/

    suspend fun insertProgress(progress: Progress) {
        return database.questionsDatabaseDao.insertProgress(progress)
    }

    suspend fun loadProgress(topic: String) : Progress {
        return database.questionsDatabaseDao.loadProgress(topic)
    }

    suspend fun deleteProgress(topic: String) {
        return database.questionsDatabaseDao.deleteProgress(topic)
    }

}