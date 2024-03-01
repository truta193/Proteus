package com.truta.proteus_android.repository

import android.util.Log
import androidx.compose.ui.graphics.Color
import com.google.firebase.firestore.dataObjects
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.truta.proteus_android.model.ScheduleDao
import com.truta.proteus_android.model.ScheduleModel
import com.truta.proteus_android.model.TaskModel
import com.truta.proteus_android.service.AuthenticationService
import com.truta.proteus_android.service.MappingService
import com.truta.proteus_android.service.StorageService
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import java.time.LocalDateTime
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
}