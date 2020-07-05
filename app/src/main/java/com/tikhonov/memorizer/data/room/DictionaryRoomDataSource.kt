package com.tikhonov.memorizer.data.room

import androidx.lifecycle.LiveData
import com.tikhonov.memorizer.data.datasource.DictionaryDataSource
import com.tikhonov.memorizer.data.model.Dictionary
import com.tikhonov.memorizer.data.model.DictionaryWithQuestions
import javax.inject.Inject

class DictionaryRoomDataSource @Inject constructor(val dictionaryDao: DictionaryDao): DictionaryDataSource {
    override suspend fun insert(dictionary: Dictionary) {
        dictionaryDao.insert(dictionary)
    }

    override suspend fun delete(dictionary: Dictionary) {
        dictionaryDao.delete(dictionary)
    }

    override fun getDictionariesLiveData(): LiveData<List<DictionaryWithQuestions>> {
        return dictionaryDao.getDictionariesLiveData()
    }

    override suspend fun getDictionary(dictionaryId: Int): Dictionary? {
        return dictionaryDao.getDictionary(dictionaryId)
    }
}