package com.tikhonov.memorizer.data.repository

import androidx.lifecycle.LiveData
import com.tikhonov.memorizer.data.datasource.DictionaryDataSource
import com.tikhonov.memorizer.data.model.Dictionary
import com.tikhonov.memorizer.data.model.DictionaryWithQuestions
import javax.inject.Inject

class DictionaryRepository @Inject constructor(val dictionaryDataSourse: DictionaryDataSource){

    suspend fun insert(dictionary: Dictionary) {
        dictionaryDataSourse.insert(dictionary)
    }

    suspend fun delete(dictionary: Dictionary) {
        dictionaryDataSourse.delete(dictionary)
    }

    fun getDictionariesLiveData(): LiveData<List<DictionaryWithQuestions>> {
        return dictionaryDataSourse.getDictionariesLiveData()
    }

    suspend fun getDictionary(dictionaryId: Int): Dictionary? {
        return dictionaryDataSourse.getDictionary(dictionaryId)
    }
}