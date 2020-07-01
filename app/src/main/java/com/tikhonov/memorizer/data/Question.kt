package com.tikhonov.memorizer.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "question", primaryKeys = ["id", "dictionaryId"])
data class Question (
    var id: String = "",
    var title: String = "",
    var answer: String = "",
    var link: String = "",
    var dateAdded: String = "",
    var dictionaryId: Int
)