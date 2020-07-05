package com.tikhonov.memorizer.data.googleDocs

import com.tikhonov.memorizer.data.datasource.DictionaryLoadDataSource
import com.tikhonov.memorizer.data.model.Dictionary
import com.tikhonov.memorizer.data.model.Question
import javax.inject.Inject

class DictionaryLoadGoogleDocsDataSource @Inject constructor(val googleDocsManager: GoogleDocsManager): DictionaryLoadDataSource {
    override suspend fun loadDocumentText(dictionary: Dictionary): List<Question> {
        return googleDocsManager.loadDocumentText(dictionary)
    }

    override suspend fun loadDocumentWords(dictionary: Dictionary): List<Question> {
        return googleDocsManager.loadDocumentWords(dictionary)
    }


}