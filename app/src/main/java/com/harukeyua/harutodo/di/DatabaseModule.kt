package com.harukeyua.harutodo.di

import android.content.Context
import com.harukeyua.harutodo.db.TasksDb
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideTasksDatabase(@ApplicationContext context: Context) = TasksDb.getInstance(context)

    @Provides
    fun provideTasksDao(tasksDb: TasksDb) = tasksDb.tasksDao()
}