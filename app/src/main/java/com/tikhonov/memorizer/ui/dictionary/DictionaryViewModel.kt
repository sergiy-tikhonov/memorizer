package com.tikhonov.memorizer.ui.dictionary

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tikhonov.memorizer.data.model.Dictionary
import com.tikhonov.memorizer.data.repository.DictionaryRepository
import com.tikhonov.memorizer.data.repository.QuestionRepository
import kotlinx.coroutines.launch

class DictionaryViewModel @ViewModelInject constructor(
    val dictionaryRepository: DictionaryRepository,
    val questionRepository: QuestionRepository
) : ViewModel() {
    val selectedDictionary:MutableLiveData<Dictionary> = MutableLiveData()

    fun saveDictionary(dictionary: Dictionary) {
        viewModelScope.launch {
            dictionaryRepository.insert(dictionary)
        }
    }
}
