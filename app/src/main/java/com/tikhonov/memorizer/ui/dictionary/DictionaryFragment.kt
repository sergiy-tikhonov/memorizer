package com.tikhonov.memorizer.ui.dictionary

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.tikhonov.memorizer.BaseFragment
import com.tikhonov.memorizer.R
import com.tikhonov.memorizer.SingleActivity
import com.tikhonov.memorizer.data.AppDatabase
import com.tikhonov.memorizer.data.Dictionary
import com.tikhonov.memorizer.data.DictionaryType
import kotlinx.android.synthetic.main.dictionary_fragment.*
import kotlinx.android.synthetic.main.toolbar.toolbar
import java.util.*


class DictionaryFragment : BaseFragment() {

    companion object {
        fun newInstance() =
            DictionaryFragment()
    }

    override fun getToolbarName(): String {
        return "DictionaryFragment"
    }

    val viewModel by activityViewModels<DictionaryListViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dictionary_fragment, container, false)
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        viewModel.selectedDictionary?.let {
            editTextDescription.setText(it.description)
            editTextDictionaryName.setText(it.name)
            editTextDocumentId.setText(it.documentId)
            editTextCode.setText(it.id.toString())
            btnManual.isChecked = it.dictionaryType == DictionaryType.MANUAL
            btnGoogleDocsText.isChecked = it.dictionaryType == DictionaryType.GOOGLE_DOCS_TEXT
            btnGoogleDocsWords.isChecked = it.dictionaryType == DictionaryType.GOOGLE_DOCS_WORDS
        }

        val parentActivity = requireActivity() as SingleActivity
        parentActivity.setSupportActionBar(toolbar)
        setHasOptionsMenu(true)
        parentActivity.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        parentActivity.supportActionBar?.setDisplayShowHomeEnabled(true)

        toolbar.setNavigationOnClickListener {
            parentActivity.onBackPressed()
        }

        buttonSave.setOnClickListener {
            val dictionary = Dictionary(
                id = editTextCode.text.toString().toInt(),
                name = editTextDictionaryName.text.toString(),
                description = editTextDescription.text.toString(),
                dateSync = Date(),
                documentId = editTextDocumentId.text.toString(),
                dictionaryType = when (toggleGroup2.checkedButtonId) {
                    R.id.btnGoogleDocsText -> DictionaryType.GOOGLE_DOCS_TEXT
                    else -> DictionaryType.MANUAL
                }
            )
            viewModel.saveDictionary(AppDatabase.getInstance(requireContext()), dictionary)
        }
    }
}