package com.tikhonov.memorizer.data.room

import com.tikhonov.memorizer.data.datasource.QuestionDataSource
import com.tikhonov.memorizer.data.model.Question
import com.tikhonov.memorizer.data.model.QuestionMark
import com.tikhonov.memorizer.data.model.QuestionWithMarks
import javax.inject.Inject

class QuestionRoomDataSource @Inject constructor(val questionDao: QuestionDao): QuestionDataSource {
    override suspend fun insertQuestionList(questionList: List<Question>) {
        questionDao.insertQuestionList(questionList)
    }

    override suspend fun deleteQuestionsWithDocumentId(dictionaryId: Int) {
        questionDao.deleteQuestionsWithDocumentId(dictionaryId)
    }

    override suspend fun getAllQuestions(dictionaryId: Int): List<Question> {
        return questionDao.getAllQuestions(dictionaryId)
    }

    override suspend fun insertQuestionMark(questionMark: QuestionMark) {
        return questionDao.insertQuestionMark(questionMark)
    }

    override suspend fun getAllQuestionsWithMarks(dictionaryId: Int): List<QuestionWithMarks> {
        return questionDao.getAllQuestionsWithMarks(dictionaryId)
    }
}