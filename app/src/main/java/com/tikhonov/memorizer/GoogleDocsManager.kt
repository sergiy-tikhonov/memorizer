package com.tikhonov.memorizer

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
import com.tikhonov.memorizer.data.AppDatabase
import com.tikhonov.memorizer.data.Question
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*

object GoogleDocsManager {

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
                    context, Collections.singleton(DriveScopes.DRIVE_FILE)
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

    fun getIntentForGoogleDocsPicker(): Intent {
        //if (googleDriveService != null) {
            //Log.d(FragmentActivity.TAG, "Opening file picker.")
            //val pickerIntent: Intent = mDriveServiceHelper.createFilePickerIntent()

            /* val builder = OpenFileActivityOptions.Builder()
             if (config.mimeTypes != null) {
                 builder.setMimeType(config.mimeTypes)
             } else {
                 builder.setMimeType(documentMimeTypes)
             }
             if (config.activityTitle != null && config.activityTitle.isNotEmpty()) {
                 builder.setActivityTitle(config.activityTitle)
             }
             if (driveId != null) {
                 builder.setActivityStartFolder(driveId)
             }
             val openOptions = builder.build()*/


            val pickerIntent = Intent(Intent.ACTION_OPEN_DOCUMENT)
            //val pickerIntent = Intent(Intent.ACTION_GET_CONTENT)
            //pickerIntent.addCategory(Intent.CATEGORY_OPENABLE)
            //pickerIntent.flags = Intent.
            pickerIntent.type = "*/*"
            //pickerIntent.type = "application/vnd.google-apps.document"
            val mimeTypes = arrayOf(
                //"vnd.google-apps.document"
                "application/vnd.google-apps.document"
                //"application/vnd.google-apps.file"
            )
            //pickerIntent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)
            pickerIntent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)
            return pickerIntent


            // The result of the SAF Intent is handled in onActivityResult.
            //if (context is AppCompatActivity)
            //context.startActivityForResult(pickerIntent, REQUEST_CODE_OPEN_DOCUMENT)
        }
    //}

    private fun readParagraphElement(element: ParagraphElement): String? {
        val run = element.textRun
        var rawText = if (run == null || run.content == null) {
            // The TextRun can be null if there is an inline object.
            ""
        } else run.content
        //rawText = rawText.replace("\n","<br>")
        if (run.textStyle.contains("bold"))
            rawText = "<b>$rawText</b>"

        return rawText
        //run.textStyle.contains("bold")
        /* return if (run == null || run.content == null) {
             // The TextRun can be null if there is an inline object.
             ""
         } else run.content*/
    }

    suspend fun loadDocument(db: AppDatabase, documentId: String, dictionaryId: Int) {

        withContext(Dispatchers.IO) {

            db.questionDAO().deleteQuestions()

            val doc: Document = googleDocsService!!.documents()[documentId].execute()

            Log.v("TestExample", "Title: ${doc.title}")

            val tables = doc.body.content.filter { it.table != null }
            Log.v("TestExample", "Items' size: ${tables.size}")

            for ((tableIndex, currentTable) in tables.withIndex()) {
                Log.v("TestExample", "Question: $tableIndex}")
                val question = Question(dictionaryId = dictionaryId)
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
                                val someText = readParagraphElement(currentTextParagraph)
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

                db.questionDAO().insertQuestion(question)

            }

        }

    }

}