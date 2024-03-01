package com.truta.proteus_android.service

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.truta.proteus_android.model.ScheduleDao
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

    override suspend fun getSchedule(id: String): ScheduleModel? {
        val document = database.collection(SCHEDULES_COLLECTION).document(id).get().await()
        if (!document.exists()) {
            return null
        }
        val scheduleDao = document.toObject(ScheduleDao::class.java) ?: return null

        return mappingService.scheduleDaoToModel(scheduleDao)
    }

    override suspend fun getScheduleByUserId(userId: String): List<ScheduleModel> {
        val schedules = database.collection(SCHEDULES_COLLECTION)
            .whereEqualTo("userId", userId)
            .get().await()

        var scheduleModels = emptyList<ScheduleModel>()

        scheduleModels = schedules.documents
            .mapNotNull { it.toObject(ScheduleDao::class.java) }
            .map { mappingService.scheduleDaoToModel(it) }

        return scheduleModels
    }

    companion object {
        private const val SCHEDULES_COLLECTION = "schedules"
    }
}