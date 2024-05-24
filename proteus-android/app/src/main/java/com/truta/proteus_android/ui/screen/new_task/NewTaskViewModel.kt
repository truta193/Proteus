package com.truta.proteus_android.ui.screen.new_task

import androidx.compose.ui.graphics.Color
import com.truta.proteus_android.Routes
import com.truta.proteus_android.model.TaskModel
import com.truta.proteus_android.service.AuthenticationService
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
    val storageService: StorageService,
    val authenticationService: AuthenticationService
) : AppViewModel() {
    val task = MutableStateFlow(TaskModel(id = "default5"))


    val possibleDays =
        listOf("Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday")

    fun initialize(taskId: String, restartApp: (String) -> Unit) {
        launchCatching(
            block = {
                authenticationService.currentUser.collect { user ->
                    if (user == null) restartApp(Routes.SignInScreen.route)
                }
            }
        )
        launchCatching(block = {
            task.value = storageService.getTaskFromCurrentScheduleById(taskId)
                ?: TaskModel(
                    id = storageService.getSchedule(storageService.getCurrentScheduleId())!!.tasks.size.toString(),
                    title = "",
                    day = LocalDate.now().dayOfWeek.value - 1,
                    color = Color.Red,
                    startTime = LocalTime.now(),
                    endTime = LocalTime.now().plusHours(1)
                )
        })
    }

    fun updateDay(newDay: Int) {
        task.value = task.value.copy(day = newDay)
    }

    fun updateStartTime(newStartTime: LocalTime) {
        task.value = task.value.copy(startTime = newStartTime)
    }

    fun updateEndTime(newEndTime: LocalTime) {
        task.value = task.value.copy(endTime = newEndTime)
    }

    fun updateTitle(newTitle: String) {
        task.value = task.value.copy(title = newTitle)
    }

    fun updateColor(newColor: Color) {
        task.value = task.value.copy(color = newColor)
    }

    fun updateColor(newColor: ULong) {
        task.value = task.value.copy(color = Color(newColor))
    }

    fun deleteTask() {
        launchCatching(
            block = {
                val scheduleId = storageService.getCurrentScheduleId()
                val schedule = storageService.getSchedule(scheduleId)
                if (schedule != null) {
                    val newTasks = schedule.tasks.toMutableList()
                    newTasks.removeIf { it.id == task.value.id }
                    schedule.tasks = newTasks
                    storageService.updateSchedule(schedule)
                }
            }
        )
    }

    fun addTask() {
        launchCatching(
            block = {
                val newTask = mappingService.taskModelToDao(task.value)
                val scheduleId = storageService.getCurrentScheduleId()
                val schedule = storageService.getSchedule(scheduleId)
                if (schedule != null) {
                    val newTasks = schedule.tasks.toMutableList()
                    newTasks.add(mappingService.taskDaoToModel(newTask))
                    schedule.tasks = newTasks
                    storageService.updateSchedule(schedule)
                }
            }
        )
    }
}