package com.truta.proteus_android.service

import com.google.firebase.firestore.FirebaseFirestore
import com.truta.proteus_android.model.ScheduleModel

interface IStorageService {
    val database: FirebaseFirestore

    suspend fun addSchedule(schedule: ScheduleModel)
    suspend fun getSchedule(id: String): ScheduleModel?
    suspend fun updateSchedule(schedule: ScheduleModel)

    //Probs unnecessary
    suspend fun updateScheduleTasks(schedule: ScheduleModel)
    suspend fun deleteSchedule(id: String)
    suspend fun getCurrentScheduleId(): String
}