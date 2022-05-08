package com.jpdevzone.younghunter.database.models

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.jpdevzone.younghunter.database.converters.IntTypeConverter
import kotlinx.parcelize.Parcelize

@Entity(tableName = "progress")
@Parcelize
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
) : Parcelable