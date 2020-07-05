package com.tikhonov.memorizer.ui.question

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import com.tikhonov.memorizer.R
import com.tikhonov.memorizer.data.model.Question
import com.tikhonov.memorizer.util.setToolbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.question_list_fragment.toolbar
import kotlinx.android.synthetic.main.question_list_fragment.*

@AndroidEntryPoint
class QuestionListFragment : Fragment(), QuestionListAdapter.OnClickListener {

    private val viewModel by viewModels<QuestionListViewModel>()
    private val args: QuestionListFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.question_list_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        setToolbar(toolbar = toolbar, showMenu = false)

        viewModel.setDictionary(args.dictionary)

        val questionListAdapter = QuestionListAdapter(this)
        recViewQuestion.apply {
            adapter = questionListAdapter
            layoutManager = LinearLayoutManager(view.context)
            addItemDecoration(DividerItemDecoration(requireContext(), RecyclerView.VERTICAL))
            setHasFixedSize(true)
        }

        viewModel.questions.observe(viewLifecycleOwner, Observer {
            questionListAdapter.setQuestionItems(it)
        })

    }

    override fun onLongClick(question: Question) {
        findNavController().navigate(QuestionListFragmentDirections.questionListQuestionTrain(dictionary = viewModel.selectedDictionary, question = question))
    }
}
