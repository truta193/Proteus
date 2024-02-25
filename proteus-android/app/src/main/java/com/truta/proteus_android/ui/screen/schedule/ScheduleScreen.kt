package com.truta.proteus_android.ui.screen.schedule

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.ParentDataModifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import com.truta.proteus_android.model.TaskModel
import com.truta.proteus_android.ui.component.Schedule
import com.truta.proteus_android.ui.component.ScheduleHeader
import com.truta.proteus_android.ui.component.ScheduleSidebar
import com.truta.proteus_android.ui.component.Task
import java.time.LocalTime
import java.time.temporal.ChronoUnit
import kotlin.math.roundToInt

@Composable
fun ScheduleScreen(
    modifier: Modifier = Modifier,
    openAndPopUp: (String, String) -> Unit,
    viewModel: ScheduleViewModel = hiltViewModel()
) {
    val tasks = viewModel.schedules[0].tasks
    val hourHeight = 64.dp
    val dayWidth = 64.dp
    val numDays = 7
    var sidebarWidth by remember { mutableIntStateOf(0) }
    val verticalScrollState = rememberScrollState()
    val horizontalScrollState = rememberScrollState()

    Column(
        modifier = modifier
    ) {
        ScheduleHeader(
            dayWidth = dayWidth,
            numDays = numDays,
            modifier = Modifier
                .padding(start = with(LocalDensity.current) { sidebarWidth.toDp() })
                .horizontalScroll(horizontalScrollState)
        )
        Row(modifier = Modifier.weight(1f)) {
            ScheduleSidebar(
                hourHeight = hourHeight,
                modifier = Modifier
                    .verticalScroll(verticalScrollState)
                    .onGloballyPositioned { sidebarWidth = it.size.width }
            )
            Schedule(
                tasks = tasks,
                dayWidth = dayWidth,
                hourHeight = hourHeight,
                numDays = numDays,
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(verticalScrollState)
                    .horizontalScroll(horizontalScrollState)
            )
        }
    }
}