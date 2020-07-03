package com.tikhonov.memorizer

import android.app.Application
import com.tikhonov.memorizer.ui.question.QuestionViewModel
import dagger.hilt.android.HiltAndroidApp
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel


import org.koin.core.context.startKoin
import org.koin.dsl.module

@HiltAndroidApp
class MemoApplication : Application(){
    override fun onCreate() {
        super.onCreate()
    }
}

