package com.truta.proteus_android.model

import java.time.LocalDateTime

data class TaskDao(
    val id: String = "",
    val title: String = "",
    val color: ULong = 0uL,
    val abbreviation: String = "",
    val startTime: String = "",
    val endTime: String = "",
    val day: Int = 0,
    val location: String = "",
    val week: Int = 0
) {
    constructor() : this("", "", 0uL, "", "", "", 0, "", 0)
}