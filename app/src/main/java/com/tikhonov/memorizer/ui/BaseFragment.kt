package com.tikhonov.memorizer.ui

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

import androidx.fragment.app.Fragment

abstract class BaseFragment: Fragment() {

    fun setToolbar(toolbar: Toolbar, showMenu: Boolean = false) {
        val parentActivity = requireActivity() as AppCompatActivity
        parentActivity.setSupportActionBar(toolbar)
        if (showMenu) {
            setHasOptionsMenu(true)
        }
    }
}