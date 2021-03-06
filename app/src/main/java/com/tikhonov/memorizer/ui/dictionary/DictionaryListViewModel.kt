package com.tikhonov.memorizer.ui.dictionary

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tikhonov.memorizer.data.model.Dictionary
import com.tikhonov.memorizer.data.repository.DictionaryLoadRepository
import com.tikhonov.memorizer.data.repository.DictionaryRepository
import com.tikhonov.memorizer.data.repository.QuestionRepository
import kotlinx.coroutines.launch

class DictionaryListViewModel @ViewModelInject constructor(
    val dictionaryRepository: DictionaryRepository,
    val questionRepository: QuestionRepository,
    val dictionaryLoadRepository: DictionaryLoadRepository
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
            val questionList = dictionaryLoadRepository.loadDocumentText(dictionary)
            questionRepository.deleteQuestionsWithDocumentId(dictionary.id)
            questionRepository.insertQuestionList(questionList)
        }
    }

    fun syncDictionaryWords(dictionary: Dictionary) {
        viewModelScope.launch {
            val questionList = dictionaryLoadRepository.loadDocumentWords(dictionary)
            questionRepository.deleteQuestionsWithDocumentId(dictionary.id)
            questionRepository.insertQuestionList(questionList)
        }
    }
}
