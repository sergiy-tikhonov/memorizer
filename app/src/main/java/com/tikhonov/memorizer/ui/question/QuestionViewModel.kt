package com.tikhonov.memorizer.ui.question

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tikhonov.memorizer.Event
import com.tikhonov.memorizer.data.AppDatabase
import com.tikhonov.memorizer.data.Question
import com.tikhonov.memorizer.data.QuestionMark
import com.tikhonov.memorizer.data.QuestionRepository
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject
import kotlin.random.Random

class QuestionViewModel @ViewModelInject constructor(val questionRepository: QuestionRepository): ViewModel() {
    val title = MutableLiveData("")
    var answer = MutableLiveData("")
    val question = MutableLiveData<Question>()
    val showAnswer = MutableLiveData(false)

    lateinit var questions: List<Question>
    val message = MutableLiveData(Event(""))

    //fun getQuestions(db: AppDatabase, dictionaryId: Int) {
    fun getQuestions(dictionaryId: Int) {
        viewModelScope.launch {
            questions = questionRepository.getAllQuestions(dictionaryId)
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

    //fun markAnswer(db: AppDatabase, mark: Int) {
    fun markAnswer(mark: Int) {
        val questionMark = QuestionMark(
            id = question.value!!.id,
            dictionaryId = question.value!!.dictionaryId,
            date = Date(),
            baseMark = 5,
            mark = mark
        )
        viewModelScope.launch {
            //db.questionDAO().insertQuestionMark(questionMark)
            questionRepository.insertQuestionMark(questionMark)
        }
    }

}