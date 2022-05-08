package com.jpdevzone.younghunter.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jpdevzone.younghunter.database.models.Progress
import com.jpdevzone.younghunter.database.models.Question

@Dao
interface QuestionsDatabaseDao {
    /**
     * QUESTIONS QUERIES
     **/

    @Query("SELECT * FROM questions WHERE id = :key" )
    suspend fun get(key: Int): Question

    /**
     * PROGRESS QUERIES
     **/

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProgress(progress: Progress)

    @Query("SELECT * FROM progress WHERE topic = :topic")
    suspend fun loadProgress(topic: String): Progress

    @Query("DELETE FROM progress WHERE topic = :topic")
    suspend fun deleteProgress(topic: String)
}