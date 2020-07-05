package com.tikhonov.memorizer.data.repository

import com.tikhonov.memorizer.data.room.QuestionDao
import com.tikhonov.memorizer.data.model.Question
import com.tikhonov.memorizer.data.model.QuestionMark
import com.tikhonov.memorizer.data.model.QuestionWithMarks
import javax.inject.Inject

class QuestionRepository @Inject constructor(val questionDao: QuestionDao) {

    suspend fun insertQuestionList(questionList: List<Question>) {
        questionDao.insertQuestionList(questionList)
    }

    suspend fun deleteQuestionsWithDocumentId(dictionaryId: Int) {
        questionDao.deleteQuestionsWithDocumentId(dictionaryId)
    }

    suspend fun getAllQuestions(dictionaryId: Int): List<Question> {
        return questionDao.getAllQuestions(dictionaryId)
    }

    suspend fun insertQuestionMark(questionMark: QuestionMark) {
        return questionDao.insertQuestionMark(questionMark)
    }

    suspend fun getAllQuestionsWithMarks(dictionaryId: Int): List<QuestionWithMarks> {
        return questionDao.getAllQuestionsWithMarks(dictionaryId)
    }
}