package com.jpdevzone.younghunter.database

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import junit.framework.TestCase
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class QuestionsDatabaseTest : TestCase() {

    private lateinit var db: QuestionsDatabase
    private lateinit var dao: QuestionsDatabaseDao

    @Before
    public override fun setUp() {
        super.setUp()
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, QuestionsDatabase::class.java).build()
        dao = db.questionsDatabaseDao
    }

    @After
    fun closeDb() {
        db.close()
    }

    @Test
    fun getQuestions() {
        val question1 = Question(1,
            "В Световния Червен списък е включен:",
            "малкият корморан",
            "розовият пеликан",
            "качулатият корморан",
            1,
            "animals")
        val question2 = Question(575,
            "Ловната площ, въз основа на която се определя максималния брой ловци в ловните сдружения, е:",
            "70 ха.",
            "75 ха.",
            "80 ха.",
            1,
            "law")
        val questions = dao.getQuestions("animals")
        questions.value?.let { assertTrue(it.contains(question1)) }
        questions.value?.let { assertFalse(it.contains(question2)) }

        val getById = dao.get(1)
        println(getById)

    }
}