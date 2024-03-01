package com.truta.proteus_android.model

import androidx.compose.ui.graphics.Color
import java.time.LocalDateTime

data class TaskModel(
    val id: String = "",
    val title: String = "",
    val color: Color = Color.White,
    val abbreviation: String = "",
    val startTime: LocalDateTime = LocalDateTime.now(),
    val endTime: LocalDateTime = LocalDateTime.now(),
    val day: Int = 0,
    val location: String = "",
    val week: Int = 0
) {
    constructor() : this("", "", Color.White, "", LocalDateTime.now(), LocalDateTime.now(), 0, "", 0)
}