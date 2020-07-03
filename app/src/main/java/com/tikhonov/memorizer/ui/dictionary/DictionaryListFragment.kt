package com.tikhonov.memorizer.ui.dictionary

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.tikhonov.memorizer.*
import com.tikhonov.memorizer.data.Dictionary
import com.tikhonov.memorizer.data.DictionaryType
import com.tikhonov.memorizer.ui.BaseFragment
import com.tikhonov.memorizer.ui.SingleActivity
import com.tikhonov.memorizer.ui.question.QuestionFragment
import com.tikhonov.memorizer.ui.question.QuestionListFragment
import com.tikhonov.memorizer.util.setToolbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.dictionary_list_fragment.*
import kotlinx.android.synthetic.main.dictionary_list_fragment.toolbar


@AndroidEntryPoint
class DictionaryListFragment : Fragment() , DictionaryListAdapter.OnClickListener{

    private val viewModel by viewModels<DictionaryListViewModel>()
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
                        viewModel.syncDictionaryText(dictionary.dictionary)
                    }
                }
            val dictionariesWithGoogleWords = viewModel.dictionaryList.value?.filter { it.dictionary.dictionaryType == DictionaryType.GOOGLE_DOCS_WORDS }
            dictionariesWithGoogleWords?.let {
                for (dictionary in it) {
                    viewModel.syncDictionaryWords(dictionary.dictionary)
                }
            }
        }
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.dictionary_list_menu, menu)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        setToolbar(toolbar = toolbar, showMenu = true)

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
            findNavController().navigate(DictionaryListFragmentDirections.dictionaryListDictionaryDetails(dictionary = null))
        }

    }

    override fun onEdit(dictionary: Dictionary) {
        findNavController().navigate(DictionaryListFragmentDirections.dictionaryListDictionaryDetails(dictionary = dictionary))
    }

    override fun onTrain(dictionary: Dictionary) {
        findNavController().navigate(DictionaryListFragmentDirections.dictionaryListQuestionTrain(dictionary = dictionary, question = null))
    }

    override fun onList(dictionary: Dictionary) {
        findNavController().navigate(DictionaryListFragmentDirections.dictionaryListQuestionList(dictionary = dictionary))
    }

    override fun onDelete(dictionary: Dictionary) {
        val builder = MaterialAlertDialogBuilder(requireContext())
            .setMessage(getString(R.string.confirm_delete_item))
            .setCancelable(false)
        builder.setPositiveButton(getString(R.string.yes))
         { dialog, _ ->
             viewModel.deleteDictionary(dictionary)
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
