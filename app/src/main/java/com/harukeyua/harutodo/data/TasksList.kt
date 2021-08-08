package com.harukeyua.harutodo.data

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.harukeyua.harutodo.ui.components.iconPickerSelection

@Entity(tableName = "tasksLists")
data class TasksList(
    val title: String,
    val icon: ImageVector = Icons.Default.Check,
    // For some reason room doesn't like compose Color class with type converter, so store argb int value instead
    val colorValue: Int,
    val tasksNum: Int = 0,
    @PrimaryKey(autoGenerate = true) val id: Int = 0
) {
    val color: Color
        get() = Color(colorValue)
}


class TasksListDbConverter {
    @TypeConverter
    fun fromImageVector(icon: ImageVector): Int {
        val index = iconPickerSelection.indexOf(icon)
        return if (index == -1) 0 else index
    }

    @TypeConverter
    fun fromIntToImageVector(iconIndex: Int): ImageVector {
        return iconPickerSelection.getOrElse(iconIndex) { iconPickerSelection[0] }
    }
}
