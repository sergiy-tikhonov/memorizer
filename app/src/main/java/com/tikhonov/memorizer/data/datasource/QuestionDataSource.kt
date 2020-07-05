package com.tikhonov.memorizer.data.datasource

import com.tikhonov.memorizer.data.model.Question
import com.tikhonov.memorizer.data.model.QuestionMark
import com.tikhonov.memorizer.data.model.QuestionWithMarks

interface QuestionDataSource {

    suspend fun insertQuestionList(questionList: List<Question>)
    suspend fun deleteQuestionsWithDocumentId(dictionaryId: Int)
    suspend fun getAllQuestions(dictionaryId: Int): List<Question>
    suspend fun insertQuestionMark(questionMark: QuestionMark)
    suspend fun getAllQuestionsWithMarks(dictionaryId: Int): List<QuestionWithMarks>
}