package org.mathieu.cleanrmapi.data.local

import androidx.room.TypeConverter

/**
 * Provides [TypeConverter] functions for Room to handle unsupported types.
 *
 * This converter allows the storage and retrieval of a list of integers
 * as a comma-separated string, since Room does not support List<Int> nativement.
 */
class Converters {

    /**
     * Converts a list of integers to a comma-separated [String] for storage in the database.
     *
     * @param list The list of integers to convert.
     * @return A comma-separated string representing the list.
     */
    @TypeConverter
    fun fromIntList(list: List<Int>): String =
        list.joinToString(",")

    /**
     * Converts a comma-separated [String] from the database back to a list of integers.
     *
     * @param data The string to convert.
     * @return A list of integers parsed from the string.
     */
    @TypeConverter
    fun toIntList(data: String): List<Int> =
        if (data.isBlank()) emptyList()
        else data.split(",").mapNotNull { it.toIntOrNull() }
}
