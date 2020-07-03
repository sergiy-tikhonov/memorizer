package com.tikhonov.memorizer.ui.dictionary

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.tikhonov.memorizer.BaseFragment
import com.tikhonov.memorizer.R
import com.tikhonov.memorizer.SingleActivity
import com.tikhonov.memorizer.data.AppDatabase
import com.tikhonov.memorizer.data.Dictionary
import com.tikhonov.memorizer.data.DictionaryType
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.dictionary_fragment.*
import kotlinx.android.synthetic.main.toolbar.toolbar
import java.util.*
import androidx.lifecycle.Observer


@AndroidEntryPoint
class DictionaryFragment : BaseFragment() {

    companion object {
        fun newInstance() =
            DictionaryFragment()
    }

    val viewModel by viewModels<DictionaryViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dictionary_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        super.setToolbar(toolbar = toolbar, showUpNavigation = true, showMenu = true)

        arguments?.let {
            viewModel.getDictionary(it.getInt("dictionaryId"))
        }

        viewModel.selectedDictionary.observe(viewLifecycleOwner, Observer {
            editTextDescription.setText(it.description)
            editTextDictionaryName.setText(it.name)
            editTextDocumentId.setText(it.documentId)
            editTextCode.setText(it.id.toString())
            btnGoogleDocsText.isChecked = it.dictionaryType == DictionaryType.GOOGLE_DOCS_TEXT
            btnGoogleDocsWords.isChecked = it.dictionaryType == DictionaryType.GOOGLE_DOCS_WORDS
        })

        buttonSave.setOnClickListener {
            val dictionary = Dictionary(
                id = editTextCode.text.toString().toInt(),
                name = editTextDictionaryName.text.toString(),
                description = editTextDescription.text.toString(),
                dateSync = Date(),
                documentId = editTextDocumentId.text.toString(),
                dictionaryType = when (toggleGroup2.checkedButtonId) {
                    R.id.btnGoogleDocsText -> DictionaryType.GOOGLE_DOCS_TEXT
                    else  -> DictionaryType.GOOGLE_DOCS_WORDS
                }
            )
            viewModel.saveDictionary(dictionary)
        }
    }
}
