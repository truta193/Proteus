package com.truta.proteus_android.ui.screen.schedule

import android.util.Log
import com.truta.proteus_android.model.ScheduleModel
import com.truta.proteus_android.repository.ScheduleRepository
import com.truta.proteus_android.service.AuthenticationService
import com.truta.proteus_android.ui.screen.AppViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel

class ScheduleViewModel @Inject constructor(
    private val authenticationService: AuthenticationService,
    private val scheduleRepository: ScheduleRepository
) : AppViewModel() {
    val schedules = scheduleRepository.schedules

    val currentSchedule: Flow<ScheduleModel?> = schedules.map { schedules ->
        Log.d("ScheduleViewModel", "currentSchedule: $schedules")
        schedules.firstOrNull { it.isCurrent }
    }
}