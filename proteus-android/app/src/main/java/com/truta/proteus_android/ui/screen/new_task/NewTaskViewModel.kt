package com.truta.proteus_android.ui.screen.new_task

import androidx.compose.ui.graphics.Color
import com.truta.proteus_android.ui.screen.AppViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import java.time.LocalDate
import java.time.LocalTime
import javax.inject.Inject

@HiltViewModel
class NewTaskViewModel @Inject constructor(

) : AppViewModel() {
    val title = MutableStateFlow("")
    val day = MutableStateFlow(LocalDate.now().dayOfWeek.value - 1)
    val color = MutableStateFlow(Color.Red.value)
    val startTime = MutableStateFlow("00:00")
    val endTime = MutableStateFlow("00:00")

    val possibleDays =
        listOf("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday")

    fun updateDay(newDay: Int) {
        day.value = newDay
    }

    fun updateStartTime(newStartTime: LocalTime) {
        startTime.value = newStartTime.toString()
    }

    fun updateEndTime(newEndTime: LocalTime) {
        endTime.value = newEndTime.toString()
    }

    fun updateTitle(newTitle: String) {
        title.value = newTitle
    }

    fun updateColor(newColor: Color) {
        color.value = newColor.value
    }

    fun updateColor(newColor: ULong) {
        color.value = newColor
    }
}