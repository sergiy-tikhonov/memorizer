package com.tikhonov.memorizer.ui.question

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tikhonov.memorizer.Event
import com.tikhonov.memorizer.data.model.Dictionary
import com.tikhonov.memorizer.data.model.Question
import com.tikhonov.memorizer.data.model.QuestionMark
import com.tikhonov.memorizer.data.repository.QuestionRepository
import com.tikhonov.memorizer.util.BASE_QUESTION_MARK
import kotlinx.coroutines.launch
import java.util.*
import kotlin.random.Random

class QuestionViewModel @ViewModelInject constructor(val questionRepository: QuestionRepository): ViewModel() {
    val title = MutableLiveData("")
    var answer = MutableLiveData("")
    val question = MutableLiveData<Question>()
    lateinit var dictionary: Dictionary
    val showAnswer = MutableLiveData(false)

    lateinit var questions: List<Question>
    val message = MutableLiveData(Event(""))

    fun setParams(paramDictionary: Dictionary, paramQuestion: Question?) {
        this.dictionary = paramDictionary
        viewModelScope.launch {
            questions = questionRepository.getAllQuestions(dictionaryId = paramDictionary.id)
            if (paramQuestion == null)
                nextQuestion()
            else
                question.postValue(paramQuestion)
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

    fun markAnswer(mark: Int) {
        val questionMark = QuestionMark(
            id = question.value!!.id,
            dictionaryId = question.value!!.dictionaryId,
            date = Date(),
            baseMark = BASE_QUESTION_MARK,
            mark = mark
        )
        viewModelScope.launch {
            questionRepository.insertQuestionMark(questionMark)
        }
    }

}