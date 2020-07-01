package com.tikhonov.memorizer.data

import androidx.room.TypeConverter
import java.util.*

class DataConverter {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time?.toLong()
    }
}

class DictionaryTypeConverter {
    @TypeConverter
    fun fromInt(value: Int): DictionaryType? {
        return DictionaryType.findWithCode(value)
    }

    @TypeConverter
    fun dictionaryTypeToInt(dictionaryType: DictionaryType?): Int? {
        return dictionaryType?.code
    }
}