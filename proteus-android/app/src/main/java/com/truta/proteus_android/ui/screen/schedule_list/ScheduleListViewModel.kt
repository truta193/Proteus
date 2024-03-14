package com.truta.proteus_android.ui.screen.schedule_list

import com.truta.proteus_android.model.ScheduleModel
import com.truta.proteus_android.repository.ScheduleRepository
import com.truta.proteus_android.ui.screen.AppViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ScheduleListViewModel @Inject constructor(
    val scheduleRepository: ScheduleRepository
) : AppViewModel() {
    val schedules = scheduleRepository.schedules

    fun setCurrentSchedule(schedule: ScheduleModel) {
        launchCatching(
            block = {
                scheduleRepository.setCurrentSchedule(schedule)
            }
        )
    }

}