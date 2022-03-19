package com.jpdevzone.younghunter.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query

@Dao
interface QuestionsDatabaseDao {
    @Query("SELECT * from questions WHERE category = :category")
    fun getQuestions(category: String): LiveData<List<Question>>

    @Query("SELECT * from questions WHERE id = :key" )
    suspend fun get(key: Int): Question
}