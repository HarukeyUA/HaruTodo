package com.harukeyua.harutodo.ui.components

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.harukeyua.harutodo.ui.theme.HaruTodoTheme

val iconPickerSelection = listOf(
    Icons.Outlined.Home,
    Icons.Outlined.ShoppingCart,
    Icons.Outlined.Favorite,
    Icons.Outlined.Star,
    Icons.Outlined.Notifications
)

val colorPickerSelection = listOf(
    Color(0xFFF44336),
    Color(0xFFE91E63),
    Color(0xFF9C27B0),
    Color(0xFF673AB7),
    Color(0xFF3F51B5),
    Color(0xFF2196F3),
    Color(0xFF03A9F4),
    Color(0xFF00BCD4),
    Color(0xFF009688),
    Color(0xFF4CAF50),
    Color(0xFF8BC34A),
    Color(0xFFFFEB3B),
    Color(0xFFFFC107),
    Color(0xFFFF9800),
    Color(0xFFFF5722)
)

@ExperimentalAnimationApi
@Composable
fun ColorPickerRow(
    modifier: Modifier = Modifier,
    selectedColor: Color = colorPickerSelection[0],
    onColorSelect: (Color) -> Unit
) {
    val scrollState = rememberScrollState()
    Row(modifier = modifier.horizontalScroll(scrollState)) {
        colorPickerSelection.forEach { color ->
            val backgroundColor: Color by animateColorAsState(
                if (selectedColor == color) color.copy(
                    alpha = 0.4f
                ) else color,
                tween(durationMillis = 300, easing = FastOutSlowInEasing)
            )
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .clip(CircleShape)
                    .clickable { onColorSelect(color) }
                    .padding(4.dp)
                    .size(48.dp)
                    .border(2.dp, MaterialTheme.colors.primary, CircleShape)
                    .padding(6.dp)
                    .clip(CircleShape)
                    .background(backgroundColor)
            ) {
                androidx.compose.animation.AnimatedVisibility(visible = selectedColor == color) {
                    Icon(Icons.Outlined.Check, null, tint = color)
                }

            }
        }
    }
}

@Composable
fun IconPickerRow(
    modifier: Modifier = Modifier,
    selectedIcon: ImageVector = iconPickerSelection[0],
    color: Color = MaterialTheme.colors.primary,
    onIconSelect: (ImageVector) -> Unit,
) {
    val scrollState = rememberScrollState()
    Row(modifier = modifier.horizontalScroll(scrollState)) {
        iconPickerSelection.forEach { icon ->
            val backgroundColor: Color by animateColorAsState(
                if (selectedIcon == icon) color.copy(
                    alpha = 0.3f
                ) else color.copy(alpha = 0.05f),
                tween(durationMillis = 300, easing = FastOutSlowInEasing)
            )
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .padding(4.dp)
                    .size(48.dp)
                    .clip(MaterialTheme.shapes.medium)
                    .clickable { onIconSelect(icon) }
                    .background(backgroundColor)
            ) {
                val tint: Color by animateColorAsState(
                    if (selectedIcon == icon) color else color.copy(
                        alpha = 0.3f
                    ),
                    tween(durationMillis = 300, easing = FastOutSlowInEasing)
                )
                Icon(icon, null, tint = tint, modifier = Modifier.size(24.dp))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun IconPickerPreview() {
    HaruTodoTheme {
        IconPickerRow(selectedIcon = iconPickerSelection[0], onIconSelect = {})
    }
}

@ExperimentalAnimationApi
@Preview(showBackground = true)
@Composable
fun ColorPickerPreview() {
    HaruTodoTheme {
        ColorPickerRow(onColorSelect = {})
    }
}