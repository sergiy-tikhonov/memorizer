package com.tikhonov.memorizer.di

import com.tikhonov.memorizer.data.datasource.DictionaryDatasource
import com.tikhonov.memorizer.data.room.DictionaryRoomDataSource
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
    abstract fun bindsDictionaryDataSource(dictionaryRoomDataSource: DictionaryRoomDataSource): DictionaryDatasource
}