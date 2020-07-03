package com.tikhonov.memorizer.data

import androidx.lifecycle.LiveData
import javax.inject.Inject

class DictionaryRepository @Inject constructor(val dictionaryDao: DictionaryDao){

    suspend fun insert(dictionary: Dictionary) {
        dictionaryDao.insert(dictionary)
    }

    suspend fun delete(dictionary: Dictionary) {
        dictionaryDao.delete(dictionary)
    }

    fun getDictionariesLiveData(): LiveData<List<DictionaryWithQuestions>> {
        return dictionaryDao.getDictionariesLiveData()
    }

    suspend fun getDictionary(dictionaryId: Int): Dictionary? {
        return dictionaryDao.getDictionary(dictionaryId)
    }
}