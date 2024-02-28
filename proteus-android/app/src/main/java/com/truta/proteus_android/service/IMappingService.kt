package com.truta.proteus_android.service

import androidx.compose.ui.graphics.Color
import com.truta.proteus_android.model.ScheduleDao
import com.truta.proteus_android.model.ScheduleModel
import com.truta.proteus_android.model.TaskDao
import com.truta.proteus_android.model.TaskModel

interface IMappingService {
    fun taskModelToDao(taskModel: TaskModel): TaskDao
    fun taskDaoToModel(taskDao: TaskDao): TaskModel
    fun scheduleModelToDao(scheduleModel: ScheduleModel): ScheduleDao
    fun scheduleDaoToModel(scheduleDao: ScheduleDao): ScheduleModel
}