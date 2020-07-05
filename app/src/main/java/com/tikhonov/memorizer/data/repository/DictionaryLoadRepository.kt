package com.tikhonov.memorizer.data.repository

import com.tikhonov.memorizer.data.datasource.DictionaryLoadDataSource
import com.tikhonov.memorizer.data.model.Dictionary
import com.tikhonov.memorizer.data.model.Question
import javax.inject.Inject

class DictionaryLoadRepository @Inject constructor(val dictionaryLoadDataSource: DictionaryLoadDataSource) {
    suspend fun loadDocumentText(dictionary: Dictionary): List<Question> {
        return dictionaryLoadDataSource.loadDocumentText(dictionary)
    }
    suspend fun loadDocumentWords(dictionary: Dictionary): List<Question> {
        return dictionaryLoadDataSource.loadDocumentWords(dictionary)
    }
}