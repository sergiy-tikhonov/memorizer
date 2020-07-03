package com.tikhonov.memorizer.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface DictionaryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(dictionary: Dictionary)

    @Delete
    suspend fun delete(dictionary: Dictionary)

    @Query("select * from dictionary")
    fun getDictionariesLiveData(): LiveData<List<DictionaryWithQuestions>>

    @Query("select * from dictionary where id = :dictionaryId")
    suspend fun getDictionary(dictionaryId: Int): Dictionary?
}