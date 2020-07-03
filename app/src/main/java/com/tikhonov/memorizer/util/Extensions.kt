package com.tikhonov.memorizer.util

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment

fun Fragment.setToolbar(toolbar: Toolbar, showMenu: Boolean = false) {
    val parentActivity = requireActivity() as AppCompatActivity
    parentActivity.setSupportActionBar(toolbar)
    if (showMenu) {
        setHasOptionsMenu(true)
    }
}