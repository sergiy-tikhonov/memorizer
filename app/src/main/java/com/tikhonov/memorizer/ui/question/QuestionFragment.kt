package com.tikhonov.memorizer.ui.question

import android.os.Bundle
import android.view.LayoutInflater

import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.text.HtmlCompat
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.question_fragment_text.*
import androidx.fragment.app.viewModels
import com.tikhonov.memorizer.BaseFragment
import com.tikhonov.memorizer.EventObserver
import com.tikhonov.memorizer.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.question_fragment_text.toolbar


@AndroidEntryPoint
class QuestionFragment : BaseFragment() {

    private val viewModel: QuestionViewModel by viewModels()

    companion object {
        fun newInstance() = QuestionFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        super.setToolbar(toolbar = toolbar, showUpNavigation = true, showMenu = false)

        arguments?.let {
            viewModel.getQuestions(requireArguments().getInt("dictionaryId"), requireArguments().getString("questionId"))
        }

        buttonNextQuestion.setOnClickListener {
            viewModel.nextQuestion()
        }

        buttonShowAnswer.setOnClickListener {
            viewModel.showAnswer()
        }

        buttonMarkAnswer.setOnClickListener {
            viewModel.markAnswer(ratingAnswer.rating.toInt())
        }

        viewModel.question.observe(viewLifecycleOwner, Observer {
            textViewTitle.text = HtmlCompat.fromHtml(it.title, HtmlCompat.FROM_HTML_MODE_COMPACT)
            textViewAnswer.text = HtmlCompat.fromHtml(it.answer, HtmlCompat.FROM_HTML_MODE_COMPACT)
        })
        viewModel.showAnswer.observe(viewLifecycleOwner, Observer {
            scrollViewAnswer.visibility = if (it) View.VISIBLE else View.INVISIBLE
            ratingAnswer.visibility = if (it) View.VISIBLE else View.INVISIBLE
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
        val layoutId = arguments?.let {
            viewModel.getQuestions(it.getInt("dictionaryId"))
            if (it.getBoolean("textMode")) R.layout.question_fragment_text else R.layout.question_fragment_words
        } ?: R.layout.question_fragment_text
        return inflater.inflate(layoutId, container, false)
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
