package com.tikhonov.memorizer.ui.question

import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tikhonov.memorizer.GoogleDocsManager
import com.tikhonov.memorizer.data.AppDatabase
import com.tikhonov.memorizer.data.Dictionary
import com.tikhonov.memorizer.data.Question
import com.tikhonov.memorizer.data.QuestionRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class QuestionListViewModel @ViewModelInject constructor(val questionRepository: QuestionRepository) : ViewModel() {
    //val db = AppDatabase.getInstance(application).dictionaryDAO()
    //val dictionaryList = db.getDictionariesLiveData()
    var selectedDictionary: Dictionary? = null
    val questions = MutableLiveData<List<Question>>()

    fun getQuestions(dictionaryId: Int) {
        viewModelScope.launch {
            questions.postValue(questionRepository.getAllQuestions(dictionaryId))
            //nextQuestion()
        }
    }

    /*fun saveDictionary(db:AppDatabase, dictionary: Dictionary) {
        viewModelScope.launch {
            db.dictionaryDAO().insert(dictionary)
        }
    }

    fun deleteDictionary(db:AppDatabase, dictionary: Dictionary) {
        viewModelScope.launch {
            db.dictionaryDAO().delete(dictionary)
        }
    }



    fun syncDictionaryWords(db:AppDatabase, dictionary: Dictionary) {
        viewModelScope.launch {
            db.questionDAO().deleteQuestionsWithDocumentId(dictionary.id)
            GoogleDocsManager.loadDocumentWords(
                db,
                dictionary.documentId,
                dictionary.id
            )
        }
    }*/
}
