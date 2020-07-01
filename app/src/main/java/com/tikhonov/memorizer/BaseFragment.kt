package com.tikhonov.memorizer

import androidx.fragment.app.Fragment

abstract class BaseFragment: Fragment() {
    abstract fun getToolbarName(): String
}