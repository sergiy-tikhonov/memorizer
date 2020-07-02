package com.tikhonov.memorizer

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

import androidx.fragment.app.Fragment

abstract class BaseFragment: Fragment() {

    fun setToolbar(toolbar: Toolbar, showUpNavigation: Boolean = false, showMenu: Boolean = false) {
        val parentActivity = requireActivity() as AppCompatActivity
        parentActivity.setSupportActionBar(toolbar)
        if (showUpNavigation) {
            parentActivity.supportActionBar?.setDisplayHomeAsUpEnabled(true)
            parentActivity.supportActionBar?.setDisplayShowHomeEnabled(true)
            toolbar.setNavigationOnClickListener {
                parentActivity.onBackPressed()
            }
        }
        if (showMenu) {
            setHasOptionsMenu(true)
        }
    }
}