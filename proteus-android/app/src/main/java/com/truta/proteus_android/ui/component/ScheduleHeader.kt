package com.truta.proteus_android.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import java.time.DayOfWeek
import java.time.LocalDate

@Composable
fun ScheduleHeader(
    dayWidth: Dp,
    numDays: Int,
    modifier: Modifier = Modifier
) {
    val days = listOf("S", "M", "T", "W", "T", "F", "S")

    val today = LocalDate.now()
    val weekStartDate = today.minusDays((today.dayOfWeek.value - DayOfWeek.SUNDAY.value).toLong())
    val dates = (0 until 7).map { weekStartDate.plusDays(it.toLong()) }

    Row(modifier = modifier.background(color = MaterialTheme.colorScheme.primary)) {
        repeat(numDays) { i ->
            Box(
                modifier = Modifier
                    .width(dayWidth),
                contentAlignment = Alignment.Center
            ) {
                DayIndicator(
                    dayWidth = dayWidth,
                    day = days[i],
                    date = dates[i].dayOfMonth,
                    isCurrent = dates[i] == today)

            }

        }
    }
}

@Preview(backgroundColor = 0xFFBB4949)
@Composable
fun ScheduleHeaderPreview() {
    ScheduleHeader(dayWidth = 60.dp, numDays = 7)
}