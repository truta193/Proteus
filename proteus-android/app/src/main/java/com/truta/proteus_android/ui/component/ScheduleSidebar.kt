package com.truta.proteus_android.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import java.time.LocalTime

@Composable
fun ScheduleSidebar(
    hourHeight: Dp,
    startTime: LocalTime,
    endTime: LocalTime,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        repeat(endTime.hour - startTime.hour) { i ->
            Box(
                modifier = Modifier
                    .height(hourHeight)
                    .padding(PaddingValues(start = 8.dp, end = 8.dp))
            ) {
                Text(
                    text = startTime.plusHours(i.toLong()).hour.toString(),
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}