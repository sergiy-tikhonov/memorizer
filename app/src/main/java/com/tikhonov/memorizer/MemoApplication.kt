package com.tikhonov.memorizer

import android.app.Application
import com.tikhonov.memorizer.ui.question.QuestionViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel


import org.koin.core.context.startKoin
import org.koin.dsl.module

class MemoApplication : Application(){
    override fun onCreate() {
        super.onCreate()
        // Start Koin
        startKoin{
            androidLogger()
            androidContext(this@MemoApplication)
            modules(appModule)
        }
    }
}

val appModule = module {
    viewModel { QuestionViewModel() }
    viewModel { SingleActivityViewModel() }
}