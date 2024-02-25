package com.truta.proteus_android.repository

import androidx.compose.ui.graphics.Color
import com.truta.proteus_android.model.ScheduleModel
import com.truta.proteus_android.model.TaskModel
import java.time.LocalDateTime


class ScheduleRepository {
    public val schedules: List<ScheduleModel> = listOf(
        ScheduleModel(
            "1",
            "Schedule 1",
            listOf(
                TaskModel("1", "Task 1", Color.Blue, "T1", LocalDateTime.parse("2021-05-18T13:00:00"), LocalDateTime.parse("2021-05-18T14:00:00"), 1, "Location 1", 1),
                TaskModel("2", "Task 2", Color.Red, "T2", LocalDateTime.parse("2021-05-18T15:00:00"), LocalDateTime.parse("2021-05-18T17:00:00"), 2, "Location 2", 1),
                TaskModel("3", "Task 3", Color.Green, "T3", LocalDateTime.parse("2021-05-18T17:00:00"), LocalDateTime.parse("2021-05-18T19:00:00"), 1, "Location 3", 1),
            ),
        )
    )
}