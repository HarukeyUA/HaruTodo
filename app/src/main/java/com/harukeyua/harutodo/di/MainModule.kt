package com.harukeyua.harutodo.di

import com.harukeyua.harutodo.db.TasksDb
import com.harukeyua.harutodo.repo.TasksRepo
import com.harukeyua.harutodo.viewModels.MainScreenViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val mainModule = module {
    single {
        TasksDb.getInstance(androidContext()).tasksDao()
    }

    factory { TasksRepo(get()) }

    viewModel { MainScreenViewModel(get()) }
}