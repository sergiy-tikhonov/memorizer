package com.tikhonov.memorizer.ui.question

import android.os.Bundle
import android.view.LayoutInflater

import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.tikhonov.memorizer.data.AppDatabase
import kotlinx.android.synthetic.main.question_fragment.*
import androidx.fragment.app.viewModels
import com.tikhonov.memorizer.EventObserver
import com.tikhonov.memorizer.R


class QuestionFragment : Fragment() {

    val viewModel: QuestionViewModel by viewModels()

    companion object {
        fun newInstance() = QuestionFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        val db = AppDatabase.getInstance(requireContext())

        arguments?.let {
            viewModel.getQuestions(db, requireArguments().getInt("dictionaryId"))
        }

        buttonNextQuestion.setOnClickListener {
            nextQuestion()
        }

        buttonShowAnswer.setOnClickListener {
            showAnswer()
        }

        viewModel.question.observe(viewLifecycleOwner, Observer {
            textViewTitle.text = HtmlCompat.fromHtml(it.title, HtmlCompat.FROM_HTML_MODE_COMPACT)
            textViewAnswer.text = HtmlCompat.fromHtml(it.answer, HtmlCompat.FROM_HTML_MODE_COMPACT)
        })
        viewModel.message.observe(viewLifecycleOwner,
            EventObserver {
                if (it != "") Toast.makeText(
                    requireContext(),
                    it,
                    Toast.LENGTH_SHORT
                ).show()
            })

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.question_fragment, container, false)
    }


    fun nextQuestion() {
        viewModel.nextQuestion()
    }

    fun showAnswer(){
        viewModel.showAnswer()
    }

/*    override fun onActivityResult(requestCode: Int, resultCode: Int, resultData: Intent?) {
        when (requestCode) {
            REQUEST_CODE_SIGN_IN -> if (resultCode == Activity.RESULT_OK && resultData != null) {
                GoogleDocsManager.handleSignInResult(this, resultData)
                Toast.makeText(this, "Logged in!", Toast.LENGTH_SHORT).show()
            }
            REQUEST_CODE_OPEN_DOCUMENT -> if (resultCode == Activity.RESULT_OK && resultData != null) {
                val uri: Uri? = resultData.data
                if (uri != null) {
                    Log.v("DEBUG", "Uri:  ${uri.path}")
                    val result = contentResolver.query(uri, null, null, null, null)
                    result?.moveToFirst()
                    Log.v("DEBUG", result?.getString(result.getColumnIndex("_display_name")))
                    val a = 1
                    //mainViewModel.loadDocument(db, DOCUMENT_ID_2)
                }
            }
        }

        super.onActivityResult(requestCode, resultCode, resultData)
    }*/


}
