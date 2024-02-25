package com.truta.proteus_android.repository

import com.truta.proteus_android.model.Schedule
import kotlinx.coroutines.flow.Flow


class ScheduleRepository {
    public val schedules : List<Schedule> = listOf(
        Schedule("1", "Schedule 1"),
        Schedule("2", "Schedule 2"),
        Schedule("3", "Schedule 3")
    )
}