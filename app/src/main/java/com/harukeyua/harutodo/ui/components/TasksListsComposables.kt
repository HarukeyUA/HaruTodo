package com.harukeyua.harutodo.ui.components

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.outlined.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.harukeyua.harutodo.R
import com.harukeyua.harutodo.data.TasksList

@Composable
fun MainScreenTitle(userName: String, pendingTasks: Int) {
    Column {
        Text(text = stringResource(R.string.greeting, userName))
        Text(
            text = "$pendingTasks pending tasks",
            style = MaterialTheme.typography.caption
        ) // TODO: Pending tasks number
    }
}

@ExperimentalFoundationApi
@Composable
fun TasksListsList(
    lists: List<TasksList>,
    onAddListClicked: () -> Unit,
    onItemLongClick: (TasksList) -> Unit
) {
    LazyColumn(
        contentPadding = PaddingValues(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(lists) { list ->
            TasksListItem(onItemLongClick, list)
        }
        item {
            AddNewListItem(onAddListClicked)
        }
    }
}

@Composable
fun AddNewListItem(onAddListClicked: () -> Unit) {
    Row(modifier = Modifier
        .clip(MaterialTheme.shapes.small)
        .clickable { onAddListClicked() }) {
        Icon(imageVector = Icons.Outlined.AddCircle, contentDescription = null)
        Spacer(modifier = Modifier.width(4.dp))
        Text(text = stringResource(R.string.add_new_list))
    }
}

@ExperimentalFoundationApi
@Composable
fun TasksListItem(
    onItemLongClick: (TasksList) -> Unit,
    list: TasksList
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .combinedClickable(onClick = {}, onLongClick = { onItemLongClick(list) })
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(48.dp)
                .background(
                    color = list.color.copy(alpha = 0.2f),
                    shape = RoundedCornerShape(16.dp)
                )
        ) {
            Icon(
                list.icon,
                contentDescription = null,
                tint = list.color,
                modifier = Modifier.size(24.dp)
            )
        }
        Spacer(modifier = Modifier.size(8.dp))
        Column {
            Text(text = list.title, fontWeight = FontWeight.SemiBold)
            Text(
                text = "${list.tasksNum} tasks",
                style = MaterialTheme.typography.caption
            ) // TODO: Update tasks number
        }

    }
}

@ExperimentalAnimationApi
@Composable
fun AddListBottomSheet(onSubmit: (TasksList) -> Unit) {
    val (selectedColor, setSelectedColor) = remember {
        mutableStateOf(colorPickerSelection[0])
    }
    val (listName, setListName) = rememberSaveable {
        mutableStateOf("")
    }
    val (selectedIcon, setSelectedIcon) = remember {
        mutableStateOf(Icons.Outlined.Home)
    }
    TaskListModificationBottomSheet(
        sheetTitle = stringResource(R.string.add_new_list),
        name = listName,
        onNameChange = setListName,
        color = selectedColor,
        onColorChange = setSelectedColor,
        icon = selectedIcon,
        onIconChange = setSelectedIcon
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            Button(
                enabled = listName.isNotEmpty(),
                onClick = {
                    onSubmit(TasksList(listName, selectedIcon, selectedColor.toArgb()))
                    setListName("")
                    setSelectedColor(colorPickerSelection[0])
                    setSelectedIcon(Icons.Outlined.Home)
                },
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(horizontal = 16.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Done,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = stringResource(R.string.add_button))
                }
            }
        }
    }
}

@ExperimentalAnimationApi
@Composable
fun EditListBottomSheet(tasksList: TasksList, onSubmit: (TasksList) -> Unit, onDelete: () -> Unit) {
    val (selectedColor, setSelectedColor) = remember(tasksList.title) {
        mutableStateOf(tasksList.color)
    }
    val (listName, setListName) = rememberSaveable(tasksList.title) {
        mutableStateOf(tasksList.title)
    }
    val (selectedIcon, setSelectedIcon) = remember(tasksList.title) {
        mutableStateOf(tasksList.icon)
    }
    TaskListModificationBottomSheet(
        sheetTitle = stringResource(R.string.edit_list),
        name = listName,
        onNameChange = setListName,
        color = selectedColor,
        onColorChange = setSelectedColor,
        icon = selectedIcon,
        onIconChange = setSelectedIcon
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            IconButton(
                onClick = { onDelete() },
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(horizontal = 16.dp)
                    .clip(MaterialTheme.shapes.small)
                    .background(MaterialTheme.colors.secondary)
            ) {
                Icon(Icons.Outlined.Delete, null)
            }
            Button(
                enabled = listName.isNotEmpty(),
                onClick = {
                    onSubmit(
                        tasksList.copy(
                            title = listName,
                            icon = selectedIcon,
                            colorValue = selectedColor.toArgb()
                        )
                    )
                    setListName("")
                    setSelectedColor(colorPickerSelection[0])
                    setSelectedIcon(Icons.Outlined.Home)
                },
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(horizontal = 16.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Done,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = stringResource(R.string.update_button))
                }
            }
        }
    }
}

@ExperimentalAnimationApi
@Composable
fun TaskListModificationBottomSheet(
    sheetTitle: String,
    name: String,
    onNameChange: (String) -> Unit,
    color: Color,
    onColorChange: (Color) -> Unit,
    icon: ImageVector,
    onIconChange: (ImageVector) -> Unit,
    buttonSlot: @Composable () -> Unit
) {
    val focusManager = LocalFocusManager.current
    Column {
        Spacer(modifier = Modifier.size(8.dp))
        Box(
            modifier = Modifier
                .height(4.dp)
                .width(64.dp)
                .background(MaterialTheme.colors.onSurface.copy(alpha = 0.1f), MaterialTheme.shapes.medium)
                .align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.size(16.dp))
        Text(
            text = sheetTitle,
            style = MaterialTheme.typography.h6,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        Spacer(modifier = Modifier.size(8.dp))
        OutlinedTextField(
            label = { Text(text = stringResource(R.string.tasks_list_title_label)) },
            value = name,
            onValueChange = onNameChange,
            singleLine = true,
            shape = RoundedCornerShape(16.dp),
            keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
        )
        Spacer(modifier = Modifier.size(8.dp))
        Text(
            text = stringResource(R.string.tasks_list_color_label),
            style = MaterialTheme.typography.body2,
            fontWeight = FontWeight.Medium,
            modifier = Modifier
                .padding(horizontal = 16.dp)
        )
        Spacer(modifier = Modifier.size(4.dp))
        ColorPickerRow(selectedColor = color, onColorSelect = onColorChange)
        Spacer(modifier = Modifier.size(8.dp))
        Text(
            text = stringResource(R.string.tasks_list_icon_label),
            style = MaterialTheme.typography.body2,
            fontWeight = FontWeight.Medium,
            modifier = Modifier
                .padding(horizontal = 16.dp)
        )
        Spacer(modifier = Modifier.size(4.dp))
        IconPickerRow(selectedIcon = icon, color = color, onIconSelect = onIconChange)
        Spacer(modifier = Modifier.size(24.dp))
        Card(elevation = 8.dp) {
            Column {
                Spacer(modifier = Modifier.size(16.dp))
                buttonSlot()
                Spacer(modifier = Modifier.size(16.dp))
            }
        }

    }
}