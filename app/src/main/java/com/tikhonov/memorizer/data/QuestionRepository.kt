package com.tikhonov.memorizer.data

import javax.inject.Inject

class QuestionRepository @Inject constructor(val questionDao: QuestionDao) {

    suspend fun insertQuestion(question: Question) {
        questionDao.insertQuestion(question)
    }

    suspend fun insertQuestionList(questionList: List<Question>) {
        questionDao.insertQuestionList(questionList)
    }

    suspend fun deleteQuestion(question: Question) {
        questionDao.deleteQuestion(question)
    }

    suspend fun deleteQuestionsWithDocumentId(dictionaryId: Int) {
        questionDao.deleteQuestionsWithDocumentId(dictionaryId)
    }

    suspend fun getAllQuestions(dictionaryId: Int): List<Question> {
        return questionDao.getAllQuestions(dictionaryId)
    }

    suspend fun getQuestionsId(): List<String> {
        return questionDao.getQuestionsId()
    }

    suspend fun deleteQuestions() {
        return questionDao.deleteQuestions()
    }

    suspend fun getQuestion(id: String): Question? {
        return questionDao.getQuestion(id)
    }

    suspend fun insertQuestionMark(questionMark: QuestionMark) {
        return questionDao.insertQuestionMark(questionMark)
    }
}