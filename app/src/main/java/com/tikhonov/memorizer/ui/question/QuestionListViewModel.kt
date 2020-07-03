package com.tikhonov.memorizer.ui.question

import android.app.Application
import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tikhonov.memorizer.GoogleDocsManager
import com.tikhonov.memorizer.data.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class QuestionListViewModel @ViewModelInject constructor(
    val questionRepository: QuestionRepository,
    val dictionaryRepository: DictionaryRepository
) : ViewModel() {
    var selectedDictionary: Dictionary? = null
    val questions = MutableLiveData<List<QuestionWithMarks>>()

    fun getQuestions(dictionaryId: Int) {
        viewModelScope.launch {
            val list = questionRepository.getAllQuestionsWithMarks(dictionaryId)
            questions.postValue(list)
            Log.v("DEBUG", "list with mark size: ${list.size}")
            selectedDictionary = dictionaryRepository.getDictionary(dictionaryId)
        }
    }
}
