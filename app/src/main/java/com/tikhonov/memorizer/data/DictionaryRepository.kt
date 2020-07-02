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

    suspend fun getDictionaries(): List<Dictionary> {
        return dictionaryDao.getDictionaries()
    }

    fun getDictionariesLiveData(): LiveData<List<DictionaryWithQuestions>> {
        return dictionaryDao.getDictionariesLiveData()
    }

    suspend fun getDictionary(dictionaryId: Int): Dictionary? {
        return dictionaryDao.getDictionary(dictionaryId)
    }
}