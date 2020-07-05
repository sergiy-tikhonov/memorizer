package com.tikhonov.memorizer.data.model

import androidx.room.Embedded
import androidx.room.Entity
import java.io.Serializable

@Entity(tableName = "question", primaryKeys = ["id", "dictionaryId"])
data class Question (
    var id: String = "",
    var title: String = "",
    var answer: String = "",
    var link: String = "",
    var dateAdded: String = "",
    var dictionaryId: Int
): Serializable

class QuestionWithMarks (
    @Embedded
    var question: Question,
    var mark: Int,
    var baseMark: Int
)