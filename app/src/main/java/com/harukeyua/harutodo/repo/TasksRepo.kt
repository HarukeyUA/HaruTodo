package com.harukeyua.harutodo.repo

import com.harukeyua.harutodo.data.TasksList
import com.harukeyua.harutodo.db.TasksDao


class TasksRepo(private val dao: TasksDao) {

    val tasksListFlow = dao.tasksListsFlow()

    suspend fun addTasksList(item: TasksList) {
        dao.insertTasksList(item)
    }

    suspend fun updateTasksList(item: TasksList) {
        dao.updateTasksList(item)
    }

    suspend fun deleteTasksList(item: TasksList) {
        dao.deleteTasksList(item)
    }

}