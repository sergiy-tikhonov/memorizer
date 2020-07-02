package com.tikhonov.memorizer.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.tikhonov.memorizer.R

@Database(entities = [Question::class, Dictionary::class, QuestionMark::class], version = 8)
@TypeConverters(DataConverter::class, DictionaryTypeConverter::class)
abstract class AppDatabase: RoomDatabase() {

    abstract fun questionDAO(): QuestionDao
    abstract fun dictionaryDAO(): DictionaryDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null
        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                var instance = INSTANCE
                return if (instance != null) {
                    instance
                } else {
                    instance =
                        Room.databaseBuilder(
                            context.applicationContext,
                            AppDatabase::class.java,
                            context.getString(R.string.app_name)
                        )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                    return instance
                }
            }
        }
    }

}