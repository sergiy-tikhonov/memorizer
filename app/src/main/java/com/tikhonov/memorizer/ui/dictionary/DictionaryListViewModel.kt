package com.tikhonov.memorizer.ui.dictionary

import android.app.Application
import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tikhonov.memorizer.GoogleDocsManager
import com.tikhonov.memorizer.data.AppDatabase
import com.tikhonov.memorizer.data.Dictionary
import com.tikhonov.memorizer.data.DictionaryRepository
import com.tikhonov.memorizer.data.QuestionRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

class DictionaryListViewModel @ViewModelInject constructor(
    val dictionaryRepository: DictionaryRepository,
    val questionRepository: QuestionRepository
) : ViewModel() {
    val dictionaryList = dictionaryRepository.getDictionariesLiveData()
    var selectedDictionary: Dictionary? = null

    fun saveDictionary(dictionary: Dictionary) {
        viewModelScope.launch {
            dictionaryRepository.insert(dictionary)
        }
    }

    fun deleteDictionary(dictionary: Dictionary) {
        viewModelScope.launch {
            dictionaryRepository.delete(dictionary)
        }
    }

    fun syncDictionaryText(dictionary: Dictionary) {
        viewModelScope.launch {
            val questionList = GoogleDocsManager.loadDocumentText(
                dictionary.documentId,
                dictionary.id
            )
            questionRepository.deleteQuestionsWithDocumentId(dictionary.id)
            questionRepository.insertQuestionList(questionList)
        }
    }

    fun syncDictionaryWords(dictionary: Dictionary) {
        viewModelScope.launch {
            val questionList = GoogleDocsManager.loadDocumentWords(
                dictionary.documentId,
                dictionary.id
            )
            questionRepository.deleteQuestionsWithDocumentId(dictionary.id)
            questionRepository.insertQuestionList(questionList)
        }
    }
}
