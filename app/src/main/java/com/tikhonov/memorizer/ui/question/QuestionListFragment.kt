package com.tikhonov.memorizer.ui.question

import android.os.Bundle
import android.view.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import com.tikhonov.memorizer.BaseFragment
import com.tikhonov.memorizer.R
import com.tikhonov.memorizer.SingleActivity
import com.tikhonov.memorizer.data.AppDatabase
import com.tikhonov.memorizer.data.DictionaryType
import com.tikhonov.memorizer.data.Question
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.question_list_fragment.toolbar
import kotlinx.android.synthetic.main.question_list_fragment.*

@AndroidEntryPoint
class QuestionListFragment : BaseFragment(), QuestionListAdapter.OnClickListener {

    companion object {
        fun newInstance() =
            QuestionListFragment()
    }

    private val viewModel by viewModels<QuestionListViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.question_list_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        super.setToolbar(toolbar = toolbar, showUpNavigation = true, showMenu = false)

        arguments?.let {
            viewModel.getQuestions(requireArguments().getInt("dictionaryId"))
        }

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

        /*fabNewDictionary.setOnClickListener {

            viewModel.selectedDictionary = null
            val fragmentDictionary = DictionaryFragment.newInstance()
            val parentActivity = requireActivity()
            if (parentActivity is FragmentNavigator)
                parentActivity.replaceFragment(fragmentDictionary)
        }*/

    }

    override fun onLongClick(question: Question) {
        val parentActivity = requireActivity() as SingleActivity
        val questionFragment =
            QuestionFragment.newInstance()
        val bundle = Bundle()
        bundle.putInt("dictionaryId", question.dictionaryId)
        bundle.putString("questionId", question.id)
        bundle.putBoolean("textMode", viewModel.selectedDictionary!!.dictionaryType == DictionaryType.GOOGLE_DOCS_TEXT)
        questionFragment.arguments = bundle
        parentActivity.replaceFragment(questionFragment)
    }




}
