package com.tikhonov.memorizer.data

import androidx.room.*

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

    @Query("select id from question")
    suspend fun getQuestionsId(): List<String>

    @Query("delete from question")
    suspend fun deleteQuestions()

    @Query("select * from question where id =:id")
    suspend fun getQuestion(id: String): Question?


}