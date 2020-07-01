package com.tikhonov.memorizer.ui.dictionary

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.tikhonov.memorizer.*
import com.tikhonov.memorizer.data.AppDatabase
import com.tikhonov.memorizer.data.Dictionary
import com.tikhonov.memorizer.data.DictionaryType
import com.tikhonov.memorizer.ui.question.QuestionFragment
import kotlinx.android.synthetic.main.dictionary_list_fragment.*
import kotlinx.android.synthetic.main.dictionary_list_fragment.toolbar


class DictionaryListFragment : Fragment() , DictionaryListAdapter.OnClickListener{

    companion object {
        fun newInstance() =
            DictionaryListFragment()
    }

    val viewModel by activityViewModels<DictionaryListViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dictionary_list_fragment, container, false)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menuItemSync) {

                val dictionariesWithGoogleText = viewModel.dictionaryList.value?.filter { it.dictionary.dictionaryType == DictionaryType.GOOGLE_DOCS_TEXT }
                dictionariesWithGoogleText?.let {
                    for (dictionary in it) {
                        viewModel.syncDictionary(AppDatabase.getInstance(requireContext()), dictionary.dictionary)
                    }
                }

        }
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.dictionary_list_menu, menu)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        val parentActivity = requireActivity() as SingleActivity
        parentActivity.setSupportActionBar(toolbar)


        val dictionaryListAdapter =
            DictionaryListAdapter(this)
        recViewDictionary.apply {
            adapter = dictionaryListAdapter
            layoutManager = LinearLayoutManager(view.context)
            setHasFixedSize(true)
        }

        viewModel.dictionaryList.observe(viewLifecycleOwner, Observer {
            dictionaryListAdapter.setDictionaryItems(it)
        })

        fabNewDictionary.setOnClickListener {

            viewModel.selectedDictionary = null
            val fragmentDictionary = DictionaryFragment.newInstance()
            val parentActivity = requireActivity()
            if (parentActivity is FragmentNavigator)
                parentActivity.replaceFragment(fragmentDictionary)
        }

    }

    override fun onClick(dictionary: Dictionary) {
        viewModel.selectedDictionary = dictionary
    }

    override fun onEdit(dictionary: Dictionary) {
        viewModel.selectedDictionary = dictionary
        val parentActivity = requireActivity() as SingleActivity
        val fragmentDictionary = DictionaryFragment.newInstance()
        parentActivity.replaceFragment(fragmentDictionary)
    }

    override fun onTrain(dictionary: Dictionary) {
        val parentActivity = requireActivity() as SingleActivity
        val questionFragment =
            QuestionFragment.newInstance()
        val bundle = Bundle()
        bundle.putInt("dictionaryId", dictionary.id)
        questionFragment.arguments = bundle
        parentActivity.replaceFragment(questionFragment)
    }



    override fun onDelete(dictionary: Dictionary) {
        val builder = MaterialAlertDialogBuilder(requireContext())
            .setMessage(getString(R.string.confirm_delete_item))
            .setCancelable(false)
        builder.setPositiveButton(getString(R.string.yes))
         { dialog, _ ->
            val db = AppDatabase.getInstance(requireContext())
            viewModel.deleteDictionary(db, dictionary)
            dialog.cancel()
        }
        builder.setNegativeButton(getString(R.string.cancel))
         { dialog, _ ->
            dialog.cancel()
        }
        builder.create().show()
        viewModel.selectedDictionary = dictionary

    }

}
