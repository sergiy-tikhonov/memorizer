package com.tikhonov.memorizer.data.datasource

import androidx.lifecycle.LiveData
import com.tikhonov.memorizer.data.model.Dictionary
import com.tikhonov.memorizer.data.model.DictionaryWithQuestions

interface DictionaryDatasource {
    suspend fun insert(dictionary: Dictionary)
    suspend fun delete(dictionary: Dictionary)
    fun getDictionariesLiveData(): LiveData<List<DictionaryWithQuestions>>
    suspend fun getDictionary(dictionaryId: Int): Dictionary?
}