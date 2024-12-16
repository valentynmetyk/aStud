package com.example.newsapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun Indicator(isSelected: Boolean) {
    val color = if (isSelected) Color(0xFF302e7c ) else Color.Gray.copy(alpha = 0.5f)
    Box(
        modifier = Modifier
            .padding(4.dp)
            .size(8.dp)
            .clip(CircleShape)
            .background(color)
    )
}