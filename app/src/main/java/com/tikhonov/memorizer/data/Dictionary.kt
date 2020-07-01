package com.tikhonov.memorizer.data

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import java.util.*

@Entity(tableName = "dictionary")
data class Dictionary (
    @PrimaryKey var id: Int,
    var name: String = "",
    var description: String = "",
    var dictionaryType: DictionaryType,
    var documentId: String = "",
    var dateSync: Date
)

class DictionaryWithQuestions (
    @Embedded
    var dictionary: Dictionary,
    @Relation(
        entityColumn = "dictionaryId",
        parentColumn = "id"
    )
    var questions: List<Question>
)

enum class DictionaryType(var code: Int) {
    MANUAL(0),
    GOOGLE_DOCS_TEXT(1),
    GOOGLE_DOCS_WORDS(2)
    ;

    companion object {
        fun findWithCode(code: Int): DictionaryType? {
            return values().toList().find { it.code == code }
        }
    }


}