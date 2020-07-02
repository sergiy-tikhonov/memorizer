package com.tikhonov.memorizer.data

import androidx.room.Entity
import java.util.*

@Entity(tableName = "question_mark", primaryKeys = ["id", "dictionaryId"])
data class QuestionMark (
    var id: String = "",
    var dictionaryId: Int,
    var date: Date,
    var mark: Int,
    var baseMark: Int
)