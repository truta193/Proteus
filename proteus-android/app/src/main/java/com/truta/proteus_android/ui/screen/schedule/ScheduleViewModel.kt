package com.truta.proteus_android.ui.screen.schedule

import com.truta.proteus_android.repository.ScheduleRepository
import com.truta.proteus_android.service.AuthenticationService
import com.truta.proteus_android.ui.screen.AppViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel

class ScheduleViewModel @Inject constructor(
    private val authenticationService: AuthenticationService,
    private val scheduleRepository: ScheduleRepository
): AppViewModel() {
    val schedules = scheduleRepository.schedules
}