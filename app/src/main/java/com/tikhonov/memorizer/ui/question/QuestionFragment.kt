package com.tikhonov.memorizer.ui.question

import android.os.Bundle
import android.view.LayoutInflater

import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.question_fragment_text.*
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.tikhonov.memorizer.EventObserver
import com.tikhonov.memorizer.R
import com.tikhonov.memorizer.data.model.DictionaryType
import com.tikhonov.memorizer.util.setToolbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.question_fragment_text.toolbar


@AndroidEntryPoint
class QuestionFragment : Fragment() {

    private val viewModel: QuestionViewModel by viewModels()
    private val args: QuestionFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        setToolbar(toolbar = toolbar, showMenu = false)

        viewModel.setParams(args.dictionary, args.question)

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
        val layoutId = if (args.dictionary.dictionaryType == DictionaryType.GOOGLE_DOCS_TEXT) R.layout.question_fragment_text else R.layout.question_fragment_words
            return inflater.inflate(layoutId, container, false)
    }
}
