package com.truta.proteus_android.service

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.truta.proteus_android.model.ScheduleModel
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class StorageService @Inject constructor(
    private val mappingService: MappingService
) : IStorageService {
    override val database: FirebaseFirestore = Firebase.firestore
    override suspend fun addSchedule(schedule: ScheduleModel) {
        val scheduleDao = mappingService.scheduleModelToDao(schedule)
        database.collection(SCHEDULES_COLLECTION).add(scheduleDao).await()
    }

    companion object {
        private const val SCHEDULES_COLLECTION = "schedules"
    }
}