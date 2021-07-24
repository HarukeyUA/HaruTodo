package com.harukeyua.harutodo.db

import androidx.room.*
import com.harukeyua.harutodo.data.TasksList
import kotlinx.coroutines.flow.Flow

@Dao
interface TasksDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTasksList(tasksList: TasksList)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateTasksList(tasksList: TasksList)

    @Delete
    suspend fun deleteTasksList(tasksList: TasksList)

    @Query("SELECT * FROM tasksLists")
    fun tasksListsFlow(): Flow<List<TasksList>>
}