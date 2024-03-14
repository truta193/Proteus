package com.truta.proteus_android.service

import androidx.compose.ui.graphics.Color
import com.truta.proteus_android.model.ScheduleDao
import com.truta.proteus_android.model.ScheduleModel
import com.truta.proteus_android.model.TaskDao
import com.truta.proteus_android.model.TaskModel
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class MappingService @Inject constructor(): IMappingService {
    private val formatter: DateTimeFormatter = DateTimeFormatter.ISO_LOCAL_TIME
    override fun taskModelToDao(taskModel: TaskModel): TaskDao {
        return TaskDao(
            id = taskModel.id,
            title = taskModel.title,
            color = taskModel.color.value,
            abbreviation = taskModel.abbreviation,
            startTime = taskModel.startTime.format(formatter),
            endTime = taskModel.endTime.format(formatter),
            day = taskModel.day,
            location = taskModel.location,
            week = taskModel.week
        )
    }

    override fun taskDaoToModel(taskDao: TaskDao): TaskModel {
        return TaskModel(
            id = taskDao.id,
            title = taskDao.title,
            color = Color(taskDao.color),
            abbreviation = taskDao.abbreviation,
            startTime = LocalTime.parse(taskDao.startTime, formatter),
            endTime = LocalTime.parse(taskDao.endTime, formatter),
            day = taskDao.day,
            location = taskDao.location,
            week = taskDao.week
        )
    }

    override fun scheduleModelToDao(scheduleModel: ScheduleModel): ScheduleDao {
        return ScheduleDao(
            id = scheduleModel.id,
            title = scheduleModel.title,
            tasks = scheduleModel.tasks.map { taskModelToDao(it) },
            userId = scheduleModel.userId,
            isCurrent = scheduleModel.isCurrent
        )
    }

    override fun scheduleDaoToModel(scheduleDao: ScheduleDao): ScheduleModel {
        return ScheduleModel(
            id = scheduleDao.id,
            title = scheduleDao.title,
            tasks = scheduleDao.tasks.map { taskDaoToModel(it) },
            userId = scheduleDao.userId,
            isCurrent = scheduleDao.isCurrent
        )
    }
}