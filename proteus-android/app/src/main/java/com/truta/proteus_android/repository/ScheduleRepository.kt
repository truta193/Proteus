package com.truta.proteus_android.repository

import android.util.Log
import com.google.firebase.firestore.dataObjects
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.truta.proteus_android.model.ScheduleDao
import com.truta.proteus_android.model.ScheduleModel
import com.truta.proteus_android.service.AuthenticationService
import com.truta.proteus_android.service.MappingService
import com.truta.proteus_android.service.StorageService
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import javax.inject.Inject


class ScheduleRepository @Inject constructor(
    private val authenticationService: AuthenticationService,
    private val mappingService: MappingService
) {
    @OptIn(ExperimentalCoroutinesApi::class)
    val schedules: Flow<List<ScheduleModel>>
        get() =
            authenticationService.currentUser.flatMapLatest { user ->
                Log.d("ScheduleRepository", "Getting schedules for user: ${user?.id}")
                Firebase.firestore
                    .collection(StorageService.SCHEDULES_COLLECTION)
                    .whereEqualTo(StorageService.USER_ID_FIELD, user?.id)
                    .dataObjects<ScheduleDao>()
                    .map { scheduleDaoList ->
                        Log.d("ScheduleRepository", "Got schedules: ${scheduleDaoList.size}, $scheduleDaoList")
                        scheduleDaoList.map { mappingService.scheduleDaoToModel(it) }
                    }
            }

    @OptIn(ExperimentalCoroutinesApi::class)
    val currentSchedule: Flow<ScheduleModel?>
        get() =
            authenticationService.currentUser.flatMapLatest { user ->
                Firebase.firestore
                    .collection(SCHEDULES_COLLECTION)
                    .whereEqualTo(USER_ID_FIELD, user?.id)
                    .whereEqualTo("current", true)
                    .dataObjects<ScheduleDao>()
                    .map { scheduleDaoList ->
                        scheduleDaoList.firstOrNull()?.let { mappingService.scheduleDaoToModel(it) }
                    }
                    .map { it }
            }

    fun addSchedule(schedule: ScheduleModel) {
        val doc = Firebase.firestore.collection(SCHEDULES_COLLECTION).document()
        val scheduleDao = mappingService.scheduleModelToDao(schedule.copy(id = doc.id))
        doc.set(scheduleDao)
    }

//    suspend fun setCurrentSchedule(schedule: ScheduleModel) {
//        val scheduleDao = mappingService.scheduleModelToDao(schedule)
//        val currentScheduleId = getCurrentScheduleId()
//
//        Firebase.firestore.collection(SCHEDULES_COLLECTION).whereEqualTo(USER_ID_FIELD, schedule.userId).get().addOnSuccessListener { documents ->
//            for (document in documents) {
//                document.reference.update("current", false)
//            }
//        }
//        Firebase.firestore.collection(SCHEDULES_COLLECTION).document(schedule.id).set(scheduleDao)
//    }

    suspend fun getCurrentScheduleId(): String {
        val ret =
            Firebase.firestore.collection(StorageService.SCHEDULES_COLLECTION).whereEqualTo("current", true).get().await()
                .firstOrNull()
                ?: return ""
        return ret.id
    }


    companion object {
        const val SCHEDULES_COLLECTION = "schedules"
        const val USER_ID_FIELD = "userId"
    }
}