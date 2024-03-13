package com.truta.proteus_android.ui.screen.new_task

import android.util.Log
import androidx.compose.ui.graphics.Color
import com.truta.proteus_android.model.TaskDao
import com.truta.proteus_android.service.MappingService
import com.truta.proteus_android.service.StorageService
import com.truta.proteus_android.ui.screen.AppViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import java.time.LocalDate
import java.time.LocalTime
import javax.inject.Inject

@HiltViewModel
class NewTaskViewModel @Inject constructor(
    val mappingService: MappingService,
    val storageService: StorageService
) : AppViewModel() {
    val title = MutableStateFlow("")
    val day = MutableStateFlow(LocalDate.now().dayOfWeek.value - 1)
    val color = MutableStateFlow(Color.Red.value)
    val startTime = MutableStateFlow("00:00")
    val endTime = MutableStateFlow("00:00")

    val possibleDays =
        listOf("Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday")

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

    fun addTask() {
        launchCatching(
            block = {
                val newTask = TaskDao("0", title.value, color.value, title.value.substring(0, 3), startTime.value, endTime.value, day.value, "", 1)
                val scheduleId = storageService.getCurrentScheduleId()
                val schedule = storageService.getSchedule(scheduleId)
                Log.d("NewTaskViewModel", "Schedule: $schedule")
                if (schedule != null) {
                    val newTasks = schedule.tasks.toMutableList()
                    newTasks.add(mappingService.taskDaoToModel(newTask))
                    schedule.tasks = newTasks
                    storageService.updateSchedule(schedule)
                    Log.d("NewTaskViewModel", "Task added")
                }
            }
        )
    }
}