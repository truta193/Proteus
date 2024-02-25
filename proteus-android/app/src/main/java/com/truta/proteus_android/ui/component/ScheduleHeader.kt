package com.truta.proteus_android.ui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp

@Composable
fun ScheduleHeader(
    dayWidth: Dp,
    numDays: Int,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier) {
        repeat(numDays) { i ->
            Box(modifier = Modifier.width(dayWidth)) {
                Text(text = "Day $i")
            }
        }
    }
}