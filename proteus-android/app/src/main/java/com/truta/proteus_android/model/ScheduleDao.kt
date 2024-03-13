package com.truta.proteus_android.model

import com.google.firebase.firestore.PropertyName

data class ScheduleDao (
    val title: String = "",
    val tasks: List<TaskDao> = emptyList(),
    val userId: String = "",
    @get:PropertyName("current")
    @set:PropertyName("current")
    var isCurrent: Boolean = false
) {
    constructor() : this("", emptyList(), "", false)
}