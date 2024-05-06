package com.truta.proteus_android.ui.screen.schedule

import com.truta.proteus_android.Routes
import com.truta.proteus_android.model.ScheduleModel
import com.truta.proteus_android.repository.ScheduleRepository
import com.truta.proteus_android.service.AuthenticationService
import com.truta.proteus_android.ui.screen.AppViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel

class ScheduleViewModel @Inject constructor(
    private val authenticationService: AuthenticationService,
    private val scheduleRepository: ScheduleRepository
) : AppViewModel() {
    val _toastMessage = MutableSharedFlow<String>()
    val toastMessage = _toastMessage.asSharedFlow()

    val schedules = scheduleRepository.schedules

    val currentSchedule: Flow<ScheduleModel?> = schedules.map { schedules ->
        schedules.firstOrNull { it.isCurrent }
    }

    fun initialize(restartApp: (String) -> Unit) {
        launchCatching(
            block = {
                authenticationService.currentUser.collect { user ->
                    if (user == null) restartApp(Routes.SignInScreen.route)
                }
            }
        )
    }

    fun sendToastMessage(message: String) {
        launchCatching(
            block = { _toastMessage.emit(message) }
        )
    }

    fun logout() {
        launchCatching(
            block = {
                authenticationService.signOut()
            }
        )
    }
}