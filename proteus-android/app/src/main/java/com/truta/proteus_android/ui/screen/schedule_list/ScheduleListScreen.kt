package com.truta.proteus_android.ui.screen.schedule_list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.truta.proteus_android.ui.component.BottomSheet

@Composable
fun ScheduleListScreen(
    modifier: Modifier = Modifier,
    viewModel: ScheduleListViewModel = hiltViewModel(),
    onDismiss: () -> Unit,
    onAdd: () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val schedules = viewModel.schedules.collectAsState(initial = emptyList())

    BottomSheet(
        modifier = modifier,
        coroutineScope = coroutineScope,
        content = { dismissSheet ->
            Column(
                modifier = Modifier
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Row(
                    modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Select a schedule",
                        style = MaterialTheme.typography.titleLarge
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Button(onClick = {
                        onAdd()
                        dismissSheet()
                    }) {
                        Text(text = "Add new")
                    }
                }


                LazyColumn {
                    items(schedules.value) { schedule ->
                        TextButton(onClick = {
                            viewModel.setCurrentSchedule(schedule)
                            dismissSheet()
                        }) {
                            Text(text = schedule.title)
                        }
                    }
                }

            }
        },
        onDismiss = onDismiss
    )
}