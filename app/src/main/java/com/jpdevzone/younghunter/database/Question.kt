package com.jpdevzone.younghunter.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "questions")
data class Question (
    @PrimaryKey(autoGenerate = false)
    val id: Int,

    @ColumnInfo
    var question: String,

    @ColumnInfo
    val optionOne: String,

    @ColumnInfo
    val optionTwo: String,

    @ColumnInfo
    val optionThree: String,

    @ColumnInfo
    val correctAnswer: Int,

    @ColumnInfo
    val category: String)

