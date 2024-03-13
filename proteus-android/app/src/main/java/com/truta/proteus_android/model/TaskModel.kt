package com.truta.proteus_android.model

import androidx.compose.ui.graphics.Color
import java.time.LocalTime

data class TaskModel(
    val id: String = "",
    val title: String = "",
    val color: Color = Color.White,
    val abbreviation: String = "",
    val startTime: LocalTime = LocalTime.now(),
    val endTime: LocalTime = LocalTime.now(),
    val day: Int = 0,
    val location: String = "",
    val week: Int = 0
) {
    constructor() : this("", "", Color.White, "", LocalTime.now(), LocalTime.now(), 0, "", 0)
}