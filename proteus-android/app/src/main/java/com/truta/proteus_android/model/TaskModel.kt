package com.truta.proteus_android.model

import androidx.compose.ui.graphics.Color
import java.time.LocalDateTime

data class TaskModel(
    val id: String,
    val title: String,
    val color: Color,
    val abbreviation: String,
    val startTime: LocalDateTime,
    val endTime: LocalDateTime,
    val day: Int,
    val location: String,
    val week: Int
) {
}