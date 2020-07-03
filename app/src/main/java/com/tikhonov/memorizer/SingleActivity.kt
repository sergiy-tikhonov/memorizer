package com.tikhonov.memorizer

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import com.tikhonov.memorizer.ui.dictionary.DictionaryListFragment
import dagger.hilt.android.AndroidEntryPoint
import org.koin.androidx.viewmodel.ext.android.viewModel

const val REQUEST_CODE_SIGN_IN = 1
const val REQUEST_CODE_OPEN_DOCUMENT = 2

@AndroidEntryPoint
class SingleActivity : AppCompatActivity(), FragmentNavigator, GoogleDocsManager.GoogleDocsManagerListener {

    val mainViewModel: SingleActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single)

        if (!mainViewModel.signInClientIsInitialised())
        {
            mainViewModel.googleSignInClient = GoogleDocsManager.getGoogleSignInClient(this)
            startActivityForResult(mainViewModel.googleSignInClient.signInIntent, REQUEST_CODE_SIGN_IN)
        }

        if (supportFragmentManager.backStackEntryCount == 0)
        {
            val fragmentDictionaryList = DictionaryListFragment.newInstance()
            replaceFragment(fragmentDictionaryList)
        }

    }

    override fun onSuccessSignIn() {
        Toast.makeText(this, "Successful sign In", Toast.LENGTH_SHORT).show()
    }

    override fun onFailureSignIn(exception: Exception?) {
        Toast.makeText(this, "Exception during Sign In: ${exception!!.message}", Toast.LENGTH_SHORT).show()
    }

    override fun replaceFragment(fragment: Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragmentContainer, fragment)
            .addToBackStack(null)
            .commit()

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, resultData: Intent?) {
        when (requestCode) {
            REQUEST_CODE_SIGN_IN -> if (resultCode == Activity.RESULT_OK && resultData != null) {
                GoogleDocsManager.handleSignInResult(this, resultData)
                Toast.makeText(this, "Logged in!", Toast.LENGTH_SHORT).show()
            }
        }

        super.onActivityResult(requestCode, resultCode, resultData)
    }

    override fun onBackPressed() {

        val count = supportFragmentManager.backStackEntryCount
        if (count == 0) {
            super.onBackPressed()
        } else {
            supportFragmentManager.popBackStack()
        }
    }
}
