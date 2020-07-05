package com.tikhonov.memorizer.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "question_mark")
data class QuestionMark (
    @PrimaryKey(autoGenerate = true) var  uid: Int = 0,
    var id: String = "",
    var dictionaryId: Int,
    var date: Date,
    var mark: Int,
    var baseMark: Int
)