package com.truta.proteus_android.model

data class ScheduleModel(
    val id: String = "",
    val title: String = "",
    var tasks: List<TaskModel> = emptyList(),
    val userId: String = "",
    val isCurrent: Boolean = false
) {
    constructor() : this("", "", emptyList(), "", false)
}