package com.jpdevzone.younghunter.database.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.jpdevzone.younghunter.database.converters.IntTypeConverter

@Entity(tableName = "progress")
class Progress (
    @PrimaryKey
    val topic: String,

    @field:TypeConverters(IntTypeConverter::class)
    val range: List<Int>?,

    @ColumnInfo
    val position: Int?,

    @ColumnInfo
    val time: Long?,

    @ColumnInfo
    val correctAnswers: Int?,
)