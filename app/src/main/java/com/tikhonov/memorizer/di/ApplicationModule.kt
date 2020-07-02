package com.tikhonov.memorizer.di

import android.content.Context
import androidx.room.Room
import com.tikhonov.memorizer.R
import com.tikhonov.memorizer.data.AppDatabase
import com.tikhonov.memorizer.data.DictionaryDao
import com.tikhonov.memorizer.data.QuestionDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class ApplicationModule {

    @Provides
    @Singleton
    fun providesAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            context.getString(R.string.app_name)
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun providesQuestionDao(appDatabase: AppDatabase): QuestionDao {
        return appDatabase.questionDAO()
    }

    @Provides
    @Singleton
    fun providesDictionaryDao(appDatabase: AppDatabase): DictionaryDao {
        return appDatabase.dictionaryDAO()
    }
}