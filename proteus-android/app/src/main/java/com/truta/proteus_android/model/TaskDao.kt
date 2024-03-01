package com.truta.proteus_android.model

import androidx.compose.ui.graphics.Color
import com.google.firebase.firestore.PropertyName
import java.time.LocalDateTime

data class TaskDao(
    val id: String = "",
    val title: String = "",
    @get:PropertyName("color")
    @set:PropertyName("color")
    var color: ULong = Color.Blue.value,
    val abbreviation: String = "",
    val startTime: String = "",
    val endTime: String = "",
    val day: Int = 0,
    val location: String = "",
    val week: Int = 0
) {
    constructor() : this("", "", Color.Blue.value, "", "", "", 0, "", 0)
}