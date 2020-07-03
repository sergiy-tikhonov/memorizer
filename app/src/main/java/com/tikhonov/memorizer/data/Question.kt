package com.tikhonov.memorizer.data

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Relation

@Entity(tableName = "question", primaryKeys = ["id", "dictionaryId"])
data class Question (
    var id: String = "",
    var title: String = "",
    var answer: String = "",
    var link: String = "",
    var dateAdded: String = "",
    var dictionaryId: Int
)

class QuestionWithMarks (
    @Embedded
    var question: Question,
    var mark: Int,
    var baseMark: Int
)