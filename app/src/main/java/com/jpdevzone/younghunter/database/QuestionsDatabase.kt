package com.jpdevzone.younghunter.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.jpdevzone.younghunter.database.converters.IntTypeConverter
import com.jpdevzone.younghunter.database.models.Progress
import com.jpdevzone.younghunter.database.models.Question

@Database(entities = [Question::class, Progress::class], version = 1, exportSchema = false)
@TypeConverters(IntTypeConverter::class)
abstract class QuestionsDatabase: RoomDatabase() {

    abstract val questionsDatabaseDao: QuestionsDatabaseDao

    companion object {

        @Volatile
        private var INSTANCE: QuestionsDatabase? = null

        fun getInstance(context: Context): QuestionsDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        QuestionsDatabase::class.java,
                        "questions"
                    )
                        .createFromAsset("database/questions.db")
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}