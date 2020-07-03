package com.tikhonov.memorizer.ui.dictionary

import android.app.Application
import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tikhonov.memorizer.GoogleDocsManager
import com.tikhonov.memorizer.data.*
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

class DictionaryViewModel @ViewModelInject constructor(
    val dictionaryRepository: DictionaryRepository,
    val questionRepository: QuestionRepository
) : ViewModel() {
    val dictionaryList = dictionaryRepository.getDictionariesLiveData()
    val selectedDictionary:MutableLiveData<Dictionary> = MutableLiveData()

    fun saveDictionary(dictionary: Dictionary) {
        viewModelScope.launch {
            dictionaryRepository.insert(dictionary)
        }
    }

    fun getDictionary(dictionaryId: Int) {
        viewModelScope.launch {
            selectedDictionary.postValue(dictionaryRepository.getDictionary(dictionaryId))
        }
    }

}
