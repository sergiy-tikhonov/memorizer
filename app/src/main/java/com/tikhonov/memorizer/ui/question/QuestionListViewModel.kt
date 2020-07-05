package com.tikhonov.memorizer.ui.question

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tikhonov.memorizer.data.model.Dictionary
import com.tikhonov.memorizer.data.model.QuestionWithMarks
import com.tikhonov.memorizer.data.repository.QuestionRepository
import kotlinx.coroutines.launch

class QuestionListViewModel @ViewModelInject constructor(
    val questionRepository: QuestionRepository
) : ViewModel() {
    lateinit var selectedDictionary: Dictionary
    val questions = MutableLiveData<List<QuestionWithMarks>>()

    fun setDictionary(dictionary: Dictionary) {
        selectedDictionary = dictionary
        viewModelScope.launch {
            val list = questionRepository.getAllQuestionsWithMarks(dictionaryId = dictionary.id)
            questions.postValue(list)
        }
    }

}
