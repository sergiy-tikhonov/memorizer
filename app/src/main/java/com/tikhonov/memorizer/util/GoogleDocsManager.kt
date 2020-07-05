package com.tikhonov.memorizer.util

import android.content.Context
import android.content.Intent
import android.util.Log
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.Scope
import com.google.api.client.extensions.android.http.AndroidHttp
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential
import com.google.api.client.json.gson.GsonFactory
import com.google.api.services.docs.v1.Docs
import com.google.api.services.docs.v1.model.Document
import com.google.api.services.docs.v1.model.ParagraphElement
import com.google.api.services.drive.Drive
import com.google.api.services.drive.DriveScopes
import com.tikhonov.memorizer.R
import com.tikhonov.memorizer.data.model.Question
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GoogleDocsManager @Inject constructor(@ApplicationContext val appContext: Context) {

    var googleSignInClient: GoogleSignInClient? = null
    var googleDriveService: Drive? = null
    var googleDocsService: Docs? = null


    interface GoogleDocsManagerListener {
        fun onSuccessSignIn()
        fun onFailureSignIn(exception: Exception?)
    }

    fun getGoogleSignInClient(context: Context): GoogleSignInClient {
        if (googleSignInClient != null) return googleSignInClient!!
        val signInOptions =
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestScopes(Scope(DriveScopes.DRIVE_FILE))
                .requestScopes(Scope(DriveScopes.DRIVE))
                .build()
        return GoogleSignIn.getClient(context, signInOptions)

    }

    fun handleSignInResult(context: Context, result: Intent) {
        GoogleSignIn.getSignedInAccountFromIntent(result)
            .addOnSuccessListener { googleAccount: GoogleSignInAccount ->

                // Use the authenticated account to sign in to the Drive service.
                val credential: GoogleAccountCredential = GoogleAccountCredential.usingOAuth2(
                    appContext, Collections.singleton(DriveScopes.DRIVE_FILE)
                )
                credential.selectedAccount = googleAccount.account
                googleDriveService = Drive.Builder(
                    AndroidHttp.newCompatibleTransport(),
                    GsonFactory(),
                    credential
                )
                    .setApplicationName(context.getString(R.string.app_name))
                    .build()

                googleDocsService =
                    Docs.Builder(AndroidHttp.newCompatibleTransport(), GsonFactory(), credential)
                        .setApplicationName(context.getString(R.string.app_name))
                        .build()
                if (context is GoogleDocsManagerListener)
                    context.onSuccessSignIn()
            }
            .addOnFailureListener { exception: Exception? ->
                if (context is GoogleDocsManagerListener)
                    context.onFailureSignIn(exception)
            }
    }

    private fun readParagraphElement(element: ParagraphElement): String? {
        val run = element.textRun
        var rawText = if (run == null || run.content == null) {
            // The TextRun can be null if there is an inline object.
            ""
        } else run.content
        if (run.textStyle.contains("bold"))
            rawText = "<b>$rawText</b>"

        return rawText
    }

    suspend fun loadDocumentWords(documentId: String, dictionaryId: Int): List<Question> {
        val questionList = mutableListOf<Question>()
        withContext(Dispatchers.IO) {

            val doc: Document = googleDocsService!!.documents()[documentId].execute()

            val tables = doc.body.content.filter { it.table != null }

            for ((tableIndex, currentTable) in tables.withIndex()) {

                val question =
                    Question(dictionaryId = dictionaryId)
                for ((rowIndex, row) in currentTable.table.tableRows.withIndex()) {
                    val _number = row.tableCells[0].content[0].paragraph.elements[0].textRun.content.replace("\n", "").trim()
                    val _question = row.tableCells[1].content[0].paragraph.elements[0].textRun.content.replace("\n", "").trim()
                    val _answer = row.tableCells[2].content[0].paragraph.elements[0].textRun.content.replace("\n", "").trim()
                    val question =
                        Question(dictionaryId = dictionaryId)
                    question.id = _number
                    question.title = _question
                    question.answer = _answer
                    questionList.add(question)

                    }
                }
            }
        return questionList
    }

    suspend fun loadDocumentText(documentId: String, dictionaryId: Int): List<Question> {

        val questionList = mutableListOf<Question>()

        withContext(Dispatchers.IO) {

            val doc: Document = googleDocsService!!.documents()[documentId].execute()

            Log.v("TestExample", "Title: ${doc.title}")

            val tables = doc.body.content.filter { it.table != null }
            Log.v("TestExample", "Items' size: ${tables.size}")

            for ((tableIndex, currentTable) in tables.withIndex()) {
                Log.v("TestExample", "Question: $tableIndex}")
                val question =
                    Question(dictionaryId = dictionaryId)
                for ((rowIndex, row) in currentTable.table.tableRows.withIndex()) {
                    Log.v("TestExample", "Index: $rowIndex}")
                    var currentText = ""
                    var isList = false
                    var isListItem = false
                    for (currentLine in row.tableCells)
                        for (currentTextLine in currentLine.content) {
                            //for (currentLine in row.tableCells[0].content) {

                            isListItem = currentTextLine.paragraph.containsKey("bullet")
                            for (currentTextParagraph in currentTextLine.paragraph.elements) {
                                val someText =
                                    readParagraphElement(
                                        currentTextParagraph
                                    )
                                if (!isList && isListItem) currentText += "<ul>"
                                if (isListItem) {
                                    currentText += "<li> $someText</li>"
                                    isList = true
                                }
                                else
                                {
                                    currentText += someText
                                    if (isList) currentText += "</ul>"
                                    isList = false
                                }
                                Log.v("TestExample", "Text: ${someText}")
                            }

                        }
                    if (isList) currentText += "</ul>"
                    if (rowIndex == 1) {
                        currentText = currentText.replace("\n", "<br>")
                        currentText = "<div> $currentText </div>"
                    }
                    else currentText = currentText.replace("\n", "")
                    when(rowIndex) {
                        0 -> question.title = currentText
                        1 -> question.answer = currentText
                        3 -> question.link = currentText
                        4 -> question.dateAdded = currentText
                        5 -> question.id = currentText
                    }
                }
                questionList.add(question)
            }
        }
        return questionList
    }
}