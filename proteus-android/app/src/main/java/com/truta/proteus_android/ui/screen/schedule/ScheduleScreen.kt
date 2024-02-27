package com.truta.proteus_android.ui.screen.schedule

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
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

    val numDays = 7
    val startHour = LocalTime.of(7, 0)
    val endHour = LocalTime.of(22, 0)

    var sidebarWidth by remember { mutableIntStateOf(0) }
    var headerHeight by remember { mutableIntStateOf(0) }
    var totalWidth by remember { mutableIntStateOf(0) }
    var totalHeight by remember { mutableIntStateOf(0) }

    val verticalScrollState = rememberScrollState()
    val horizontalScrollState = rememberScrollState()

    Column(
        modifier = modifier
            .onGloballyPositioned {
                totalWidth = it.size.width
                totalHeight = it.size.height
            }
    ) {
        val trueDayWidth = with (LocalDensity.current) { (totalWidth.toDp() - sidebarWidth.toDp()) / numDays }
        val trueHourHeight = with (LocalDensity.current) { (totalHeight.toDp() - headerHeight.toDp()) / (endHour.hour - startHour.hour)}

        Box(
            modifier = Modifier
                .onGloballyPositioned { headerHeight = it.size.height }
                .horizontalScroll(horizontalScrollState)
                .background(color = MaterialTheme.colorScheme.secondaryContainer)
        ) {
            ScheduleHeader(
                dayWidth = trueDayWidth,
                numDays = numDays,
                modifier = Modifier
                    .padding(start = with(LocalDensity.current) { sidebarWidth.toDp() })
            )
        }

        Row(modifier = Modifier.weight(1f)) {
            ScheduleSidebar(
                hourHeight = trueHourHeight,
                startTime = startHour,
                endTime = endHour,
                modifier = Modifier
                    .verticalScroll(verticalScrollState)
                    .onGloballyPositioned { sidebarWidth = it.size.width }
            )
            Schedule(
                tasks = tasks,
                dayWidth = trueDayWidth,
                hourHeight = trueHourHeight,
                numDays = numDays,
                startTime = startHour,
                endTime = endHour,
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(verticalScrollState)
                    .horizontalScroll(horizontalScrollState)
            )
        }
    }
}