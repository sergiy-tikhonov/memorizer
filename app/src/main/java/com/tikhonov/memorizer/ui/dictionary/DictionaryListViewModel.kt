package com.tikhonov.memorizer.ui.dictionary

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tikhonov.memorizer.util.GoogleDocsManager
import com.tikhonov.memorizer.data.Dictionary
import com.tikhonov.memorizer.data.DictionaryRepository
import com.tikhonov.memorizer.data.QuestionRepository
import kotlinx.coroutines.launch

class DictionaryListViewModel @ViewModelInject constructor(
    val dictionaryRepository: DictionaryRepository,
    val questionRepository: QuestionRepository,
    val googleDocsManager: GoogleDocsManager
) : ViewModel() {
    val dictionaryList = dictionaryRepository.getDictionariesLiveData()
    var selectedDictionary: Dictionary? = null


    fun deleteDictionary(dictionary: Dictionary) {
        viewModelScope.launch {
            dictionaryRepository.delete(dictionary)
        }
    }

    fun syncDictionaryText(dictionary: Dictionary) {
        viewModelScope.launch {
            val questionList = googleDocsManager.loadDocumentText(
                dictionary.documentId,
                dictionary.id
            )
            questionRepository.deleteQuestionsWithDocumentId(dictionary.id)
            questionRepository.insertQuestionList(questionList)
        }
    }

    fun syncDictionaryWords(dictionary: Dictionary) {
        viewModelScope.launch {
            val questionList = googleDocsManager.loadDocumentWords(
                dictionary.documentId,
                dictionary.id
            )
            questionRepository.deleteQuestionsWithDocumentId(dictionary.id)
            questionRepository.insertQuestionList(questionList)
        }
    }
}
