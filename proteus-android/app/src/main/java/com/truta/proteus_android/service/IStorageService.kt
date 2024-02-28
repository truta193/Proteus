package com.truta.proteus_android.service

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.truta.proteus_android.model.ScheduleModel

interface IStorageService {
    val database: FirebaseFirestore

    suspend fun addSchedule(schedule: ScheduleModel)
}