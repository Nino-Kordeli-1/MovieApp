package com.movieapp.converter

import androidx.room.TypeConverter

class Converters {
    @TypeConverter
    fun fromGenreIds(value: List<Int>): String =
        value.joinToString(",")

    @TypeConverter
    fun toGenreIds(value: String): List<Int> =
        if (value.isBlank()) {
            emptyList()
        } else {
            value.split(",").map { it.toInt() }
        }
}