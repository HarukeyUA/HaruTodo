package com.harukeyua.harutodo.viewModels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.harukeyua.harutodo.data.TasksList
import com.harukeyua.harutodo.repo.TasksRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(private val tasksRepo: TasksRepo) : ViewModel() {

    var todoListsFlow = tasksRepo.tasksListFlow

    var todoListToEdit: TasksList? by mutableStateOf(null)

    fun submitItemForEdit(index: TasksList) {
        todoListToEdit = index
    }

    fun updateList(tasksList: TasksList) {
        viewModelScope.launch {
            tasksRepo.updateTasksList(tasksList)
        }
    }

    fun cancelUpdate() {
        todoListToEdit = null
    }

    fun addList(tasksList: TasksList) {
        viewModelScope.launch {
            tasksRepo.addTasksList(tasksList)
        }
    }

    fun deleteList(tasksList: TasksList) {
        viewModelScope.launch {
            tasksRepo.deleteTasksList(tasksList)
        }
    }

}