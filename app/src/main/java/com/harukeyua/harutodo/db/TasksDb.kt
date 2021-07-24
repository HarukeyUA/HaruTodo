package com.harukeyua.harutodo.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.harukeyua.harutodo.data.TasksList
import com.harukeyua.harutodo.data.TasksListDbConverter

@Database(entities = [TasksList::class], version = 1, exportSchema = false)
@TypeConverters(TasksListDbConverter::class)
abstract class TasksDb: RoomDatabase() {
    abstract fun tasksDao(): TasksDao

    companion object {
        private const val DATABASE_NAME = "tasks.db"

        @Volatile
        private var instance: TasksDb? = null

        private fun buildDatabase(context: Context): TasksDb {
            return Room.databaseBuilder(context, TasksDb::class.java, DATABASE_NAME).build()
        }

        fun getInstance(context: Context): TasksDb {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }
    }
}