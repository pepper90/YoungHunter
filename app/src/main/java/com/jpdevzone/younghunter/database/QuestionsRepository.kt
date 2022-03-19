package com.jpdevzone.younghunter.database

class QuestionsRepository(
    private val database: QuestionsDatabase
) {

    suspend fun getQuestion(id: Int): Question {
        return database.questionsDatabaseDao.get(id)
    }

}