package com.harukeyua.harutodo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Search
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.harukeyua.harutodo.data.TasksList
import com.harukeyua.harutodo.ui.theme.HaruTodoTheme
import com.harukeyua.harutodo.viewModels.MainScreenViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @ExperimentalAnimationApi
    @ExperimentalFoundationApi
    @ExperimentalMaterialApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HaruTodoTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    MainScreen()
                }
            }
        }
    }
}

@ExperimentalAnimationApi
@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Composable
fun MainScreen(viewModel: MainScreenViewModel = viewModel()) {
    val sheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden
    )
    var searchText by rememberSaveable { mutableStateOf("") }
    val todoListToEdit = viewModel.todoListToEdit
    val tasksLists by viewModel.todoListsFlow.collectAsState(initial = listOf())
    ModalBottomSheetLayout(
        sheetShape = RoundedCornerShape(16.dp),
        sheetState = sheetState,
        sheetContent = {
            ListsScreenSheetContent(
                todoListToEdit = todoListToEdit,
                viewModel = viewModel,
                sheetState = sheetState,
                onAddList = viewModel::addList,
                onUpdateList = viewModel::updateList,
                onDeleteList = viewModel::deleteList
            )
        }) {
        ListsScreenContent(
            searchText = searchText,
            sheetState = sheetState,
            tasksLists = tasksLists,
            onSearchQueryUpdate = { searchText = it },
            onSubmitItemForEdit = viewModel::submitItemForEdit,
            onEditCancel = viewModel::cancelUpdate
        )
    }
}

@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Composable
private fun ListsScreenContent(
    searchText: String,
    sheetState: ModalBottomSheetState,
    tasksLists: List<TasksList>,
    onSearchQueryUpdate: (String) -> Unit,
    onSubmitItemForEdit: (TasksList) -> Unit,
    onEditCancel: () -> Unit
) {
    val scope = rememberCoroutineScope()
    Column {
        TopAppBar(
            title = { MainScreenTitle(userName = "User", pendingTasks = 12) }, // TODO: Username
            backgroundColor = MaterialTheme.colors.surface,
            elevation = 0.dp,
            actions = {
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(Icons.Outlined.AccountCircle, contentDescription = null)
                }
            }
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = searchText,
            onValueChange = { onSearchQueryUpdate(it) },
            label = { Text(stringResource(R.string.search_label)) },
            trailingIcon = { Icon(Icons.Outlined.Search, contentDescription = null) },
            singleLine = true,
            shape = MaterialTheme.shapes.medium,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = stringResource(R.string.my_lists_title),
            style = MaterialTheme.typography.h5.copy(fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        TasksListsList(
            lists = tasksLists,
            onItemLongClick = { tasksList ->
                onSubmitItemForEdit(tasksList)
                scope.launch {
                    sheetState.show()
                }
            },
            onAddListClicked = {
                onEditCancel()
                scope.launch {
                    sheetState.show()
                }
            })
    }
}

@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Composable
fun ListsScreenSheetContent(
    todoListToEdit: TasksList?,
    viewModel: MainScreenViewModel,
    sheetState: ModalBottomSheetState,
    onAddList: (TasksList) -> Unit,
    onUpdateList: (TasksList) -> Unit,
    onDeleteList: (TasksList) -> Unit
) {
    val scope = rememberCoroutineScope()
    if (todoListToEdit == null)
        AddListBottomSheet { tasksList ->
            onAddList(tasksList)
            scope.launch {
                sheetState.hide()

            }
        }
    else
        EditListBottomSheet(
            tasksList = viewModel.todoListToEdit!!,
            onSubmit = { tasksList ->
                onUpdateList(tasksList)
                scope.launch {
                    sheetState.hide()

                }
            },
            onDelete = {
                onDeleteList(todoListToEdit)
                scope.launch {
                    sheetState.hide()

                }
            }
        )
}
