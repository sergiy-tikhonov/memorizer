package com.tikhonov.memorizer.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.tikhonov.memorizer.data.model.Dictionary
import com.tikhonov.memorizer.data.model.Question
import com.tikhonov.memorizer.data.model.QuestionMark

@Database(entities = [Question::class, Dictionary::class, QuestionMark::class], version = 10)
@TypeConverters(DataConverter::class, DictionaryTypeConverter::class)
abstract class AppDatabase: RoomDatabase() {
    abstract fun questionDAO(): QuestionDao
    abstract fun dictionaryDAO(): DictionaryDao
}