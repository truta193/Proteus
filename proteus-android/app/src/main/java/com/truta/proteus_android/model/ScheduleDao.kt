package com.truta.proteus_android.model

data class ScheduleDao (
    val id: String = "",
    val title: String = "",
    val tasks: List<TaskDao> = emptyList(),
    val userId: String = ""
)