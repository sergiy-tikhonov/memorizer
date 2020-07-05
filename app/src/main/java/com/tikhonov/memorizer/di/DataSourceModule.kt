package com.tikhonov.memorizer.di

import com.tikhonov.memorizer.data.datasource.DictionaryDataSource
import com.tikhonov.memorizer.data.datasource.DictionaryLoadDataSource
import com.tikhonov.memorizer.data.datasource.QuestionDataSource
import com.tikhonov.memorizer.data.googleDocs.DictionaryLoadGoogleDocsDataSource
import com.tikhonov.memorizer.data.room.DictionaryRoomDataSource
import com.tikhonov.memorizer.data.room.QuestionRoomDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
abstract class DataSourceModule {
    @Binds
    @Singleton
    abstract fun bindsDictionaryDataSource(dictionaryRoomDataSource: DictionaryRoomDataSource): DictionaryDataSource

    @Binds
    @Singleton
    abstract fun bindsQuestionDataSource(questionRoomDataSource: QuestionRoomDataSource): QuestionDataSource

    @Binds
    @Singleton
    abstract fun bindsDictionaryLoadDataSource(dictionaryLoadGoogleDocsDataSource: DictionaryLoadGoogleDocsDataSource): DictionaryLoadDataSource
}