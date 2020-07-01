package com.tikhonov.memorizer.ui.dictionary

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.tikhonov.memorizer.GoogleDocsManager
import com.tikhonov.memorizer.data.AppDatabase
import com.tikhonov.memorizer.data.Dictionary
import kotlinx.coroutines.launch

class DictionaryListViewModel(application: Application) : AndroidViewModel(application) {
    val db = AppDatabase.getInstance(application).dictionaryDAO()
    val dictionaryList = db.getDictionariesLiveData()
    var selectedDictionary: Dictionary? = null

    fun saveDictionary(db:AppDatabase, dictionary: Dictionary) {
        viewModelScope.launch {
            db.dictionaryDAO().insert(dictionary)
        }
    }

    fun deleteDictionary(db:AppDatabase, dictionary: Dictionary) {
        viewModelScope.launch {
            db.dictionaryDAO().delete(dictionary)
        }
    }

    fun syncDictionary(db:AppDatabase, dictionary: Dictionary) {
        viewModelScope.launch {
            db.questionDAO().deleteQuestionsWithDocumentId(dictionary.id)
            GoogleDocsManager.loadDocument(
                db,
                dictionary.documentId,
                dictionary.id
            )
        }
    }
}
