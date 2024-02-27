package com.truta.proteus_android.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun ScheduleHeader(
    dayWidth: Dp,
    numDays: Int,
    modifier: Modifier = Modifier
) {
    val days = listOf("Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat")
    Row(modifier = modifier.background(color = MaterialTheme.colorScheme.secondaryContainer)) {
        repeat(numDays) { i ->
            Box(modifier = Modifier.width(dayWidth).padding(bottom = 8.dp, top = 8.dp)) {
                Text(
                    text = days[i],
                    modifier = Modifier.align(Alignment.Center),
                    color = MaterialTheme.colorScheme.onSecondaryContainer
                )
            }
        }
    }
}