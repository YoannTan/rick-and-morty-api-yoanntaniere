package org.mathieu.cleanrmapi.data.local

import androidx.room.TypeConverter

class Converters {

    @TypeConverter
    fun fromIntList(list: List<Int>): String =
        list.joinToString(",")

    @TypeConverter
    fun toIntList(data: String): List<Int> =
        if (data.isBlank()) emptyList()
        else data.split(",").mapNotNull { it.toIntOrNull() }
}
