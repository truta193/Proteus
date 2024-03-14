package com.truta.proteus_android.ui.screen.new_schedule

import com.truta.proteus_android.model.ScheduleModel
import com.truta.proteus_android.repository.ScheduleRepository
import com.truta.proteus_android.service.AuthenticationService
import com.truta.proteus_android.ui.screen.AppViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class NewScheduleViewModel @Inject constructor(
    val scheduleRepository: ScheduleRepository,
    val authenticationService: AuthenticationService
) : AppViewModel(){
    val title = MutableStateFlow("")
    val titleError = MutableStateFlow(true)

    val isFormValid = MutableStateFlow(false)

    fun updateTitle(newTitle: String) {
        title.value = newTitle
        validateUi()
    }

    fun validateUi() {
        titleError.value = title.value.isEmpty()
        isFormValid.value = !titleError.value
    }

    fun addSchedule() {
        launchCatching (
            block = {
                val schedule = ScheduleModel(title = title.value, userId = authenticationService.currentUserId)
                scheduleRepository.addSchedule(schedule)
            }
        )
    }

}