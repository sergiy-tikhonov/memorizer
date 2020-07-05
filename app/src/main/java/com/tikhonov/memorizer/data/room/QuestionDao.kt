package com.tikhonov.memorizer.data.room

import androidx.room.*
import com.tikhonov.memorizer.data.model.Question
import com.tikhonov.memorizer.data.model.QuestionMark
import com.tikhonov.memorizer.data.model.QuestionWithMarks

@Dao
interface QuestionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuestion(question: Question)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuestionList(question: List<Question>)

    @Delete
    suspend fun deleteQuestion(question: Question)

    @Query("delete from question where dictionaryId = :dictionaryId")
    suspend fun deleteQuestionsWithDocumentId(dictionaryId: Int)

    @Query("select * from question where dictionaryId = :dictionaryId")
    suspend fun getAllQuestions(dictionaryId: Int): List<Question>

    @Query("delete from question")
    suspend fun deleteQuestions()

    @Query("select * from question where id =:id")
    suspend fun getQuestion(id: String): Question?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQuestionMark(questionMark: QuestionMark)

    @Transaction
    @Query("select question.*, sum(question_mark.mark/question_mark.baseMark*5) as mark, sum(question_mark.baseMark) as baseMark from question LEFT JOIN question_mark ON (question.dictionaryId=question_mark.dictionaryId and question.id=question_mark.id) where question.dictionaryId = :dictionaryId group by question.id,question.dictionaryId,question.answer,question.dateAdded,question.link,question.title")
    suspend fun getAllQuestionsWithMarks(dictionaryId: Int): List<QuestionWithMarks>
}