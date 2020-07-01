package com.tikhonov.memorizer.ui.question

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tikhonov.memorizer.Event
import com.tikhonov.memorizer.data.AppDatabase
import com.tikhonov.memorizer.data.Question
import kotlinx.coroutines.launch
import kotlin.random.Random

class QuestionViewModel: ViewModel() {
    val title = MutableLiveData("")
    var answer = MutableLiveData("")
    val question = MutableLiveData<Question>()
    val showAnswer = MutableLiveData(false)

    lateinit var questions: List<Question>
    val message = MutableLiveData(Event(""))

    fun getQuestions(db: AppDatabase, dictionaryId: Int) {
        viewModelScope.launch {
            questions = db.questionDAO().getAllQuestions(dictionaryId)
            nextQuestion()
        }
    }

    fun nextQuestion(){
        val numberOfQuestion = Random.nextInt(questions.size-1)
        question.postValue(questions[numberOfQuestion])
        showAnswer.postValue(false)
    }

    fun showAnswer(){
        showAnswer.postValue(true)
    }

}