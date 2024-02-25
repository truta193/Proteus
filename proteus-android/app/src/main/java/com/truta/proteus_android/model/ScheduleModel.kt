package com.truta.proteus_android.model

data class ScheduleModel(
    val id: String,
    val title: String,
    val tasks: List<TaskModel>
) { }