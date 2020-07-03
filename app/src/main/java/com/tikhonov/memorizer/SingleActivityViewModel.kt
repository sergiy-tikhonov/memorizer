package com.tikhonov.memorizer

import androidx.lifecycle.ViewModel
import com.google.android.gms.auth.api.signin.GoogleSignInClient

class SingleActivityViewModel: ViewModel() {
    lateinit var googleSignInClient: GoogleSignInClient

    fun signInClientIsInitialised() = this::googleSignInClient.isInitialized
}