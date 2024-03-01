package com.truta.proteus_android.service

import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.truta.proteus_android.model.ScheduleDao
import com.truta.proteus_android.model.ScheduleModel
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class StorageService @Inject constructor(
    private val mappingService: MappingService,
    private val authenticationService: AuthenticationService
) : IStorageService {
    override val database: FirebaseFirestore = Firebase.firestore

    override suspend fun addSchedule(schedule: ScheduleModel) {
        var scheduleDao = mappingService.scheduleModelToDao(schedule)
        scheduleDao = scheduleDao.copy(userId = Firebase.auth.currentUser?.uid.orEmpty())
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

    override suspend fun updateSchedule(schedule: ScheduleModel) {
        val scheduleDao = mappingService.scheduleModelToDao(schedule)
        database.collection(SCHEDULES_COLLECTION).document(schedule.id).set(scheduleDao).await()
    }

    override suspend fun deleteSchedule(id: String) {
        database.collection(SCHEDULES_COLLECTION).document(id).delete().await()
    }

    override suspend fun updateScheduleTasks(schedule: ScheduleModel) {
        val scheduleDao = mappingService.scheduleModelToDao(schedule)
        database.collection(SCHEDULES_COLLECTION).document(schedule.id).update("tasks", scheduleDao.tasks).await()
    }

    companion object {
        const val SCHEDULES_COLLECTION = "schedules"
        const val USER_ID_FIELD = "userId"
    }
}