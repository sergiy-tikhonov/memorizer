package com.tikhonov.memorizer.data.datasource

import com.tikhonov.memorizer.data.model.Dictionary
import com.tikhonov.memorizer.data.model.Question

interface DictionaryLoadDataSource {
    suspend fun loadDocumentText(dictionary: Dictionary): List<Question>
    suspend fun loadDocumentWords(dictionary: Dictionary): List<Question>
}