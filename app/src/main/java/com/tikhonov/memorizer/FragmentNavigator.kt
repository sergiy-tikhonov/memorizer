package com.tikhonov.memorizer

import androidx.fragment.app.Fragment

interface FragmentNavigator {
    fun replaceFragment(fragment: Fragment)
}