package com.tikhonov.memorizer.data.repository

import com.tikhonov.memorizer.data.datasource.QuestionDataSource
import com.tikhonov.memorizer.data.model.Question
import com.tikhonov.memorizer.data.model.QuestionMark
import com.tikhonov.memorizer.data.model.QuestionWithMarks
import javax.inject.Inject

class QuestionRepository @Inject constructor(val questionDataSource: QuestionDataSource) {

    suspend fun insertQuestionList(questionList: List<Question>) {
        questionDataSource.insertQuestionList(questionList)
    }

    suspend fun deleteQuestionsWithDocumentId(dictionaryId: Int) {
        questionDataSource.deleteQuestionsWithDocumentId(dictionaryId)
    }

    suspend fun getAllQuestions(dictionaryId: Int): List<Question> {
        return questionDataSource.getAllQuestions(dictionaryId)
    }

    suspend fun insertQuestionMark(questionMark: QuestionMark) {
        return questionDataSource.insertQuestionMark(questionMark)
    }

    suspend fun getAllQuestionsWithMarks(dictionaryId: Int): List<QuestionWithMarks> {
        return questionDataSource.getAllQuestionsWithMarks(dictionaryId)
    }
}