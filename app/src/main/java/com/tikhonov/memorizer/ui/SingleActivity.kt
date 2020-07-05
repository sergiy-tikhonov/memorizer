package com.tikhonov.memorizer.ui

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.tikhonov.memorizer.util.GoogleDocsManager
import com.tikhonov.memorizer.R
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

const val REQUEST_CODE_SIGN_IN = 1

@AndroidEntryPoint
class SingleActivity : AppCompatActivity(),
    GoogleDocsManager.GoogleDocsManagerListener {

    val mainViewModel: SingleActivityViewModel by viewModels()
    @Inject lateinit var googleDocsManager: GoogleDocsManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single)

        if (!mainViewModel.signInClientIsInitialised())
        {
            mainViewModel.googleSignInClient =
                googleDocsManager.getGoogleSignInClient(
                    this
                )
            startActivityForResult(mainViewModel.googleSignInClient.signInIntent,
                REQUEST_CODE_SIGN_IN
            )
        }
    }

    override fun onSuccessSignIn() {
        Toast.makeText(this, "Successful sign In", Toast.LENGTH_SHORT).show()
    }

    override fun onFailureSignIn(exception: Exception?) {
        Toast.makeText(this, "Exception during Sign In: ${exception!!.message}", Toast.LENGTH_SHORT).show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, resultData: Intent?) {
        when (requestCode) {
            REQUEST_CODE_SIGN_IN -> if (resultCode == Activity.RESULT_OK && resultData != null) {
                googleDocsManager.handleSignInResult(
                    this,
                    resultData
                )
                Toast.makeText(this, "Logged in!", Toast.LENGTH_SHORT).show()
            }
        }

        super.onActivityResult(requestCode, resultCode, resultData)
    }
}
