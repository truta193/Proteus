package com.truta.proteus_android.ui.screen.new_task

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.truta.proteus_android.ui.component.BottomSheet
import com.truta.proteus_android.ui.component.ColorCard
import com.truta.proteus_android.ui.component.DoubleOptionCard
import com.truta.proteus_android.ui.component.InputCard
import com.truta.proteus_android.ui.component.OptionCard
import com.truta.proteus_android.ui.component.TimePickerDialog
import java.time.LocalTime

//TODO: When selecting start time, set the state to +1 hour so the end time is always after the start time
//TODO: Same thing but -1 hour for the end time
//State is immutable so need to figure that out
//Try moving to viewmodel and/or mutableStateOf
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewTaskScreen(
    openAndPopUp: (String, String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: NewTaskViewModel = hiltViewModel()
) {
    val scope = rememberCoroutineScope()

    var showBottomSheet by rememberSaveable { mutableStateOf(false) }
    var showDaySelector by rememberSaveable { mutableStateOf(false) }
    var showColorSelector by rememberSaveable { mutableStateOf(false) }
    var showTimePicker by rememberSaveable { mutableStateOf(false) }
    var startTimeTrigger by rememberSaveable { mutableStateOf(false) }
    var endTimeTrigger by rememberSaveable { mutableStateOf(false) }

    val dayIndex = viewModel.day.collectAsState()
    val color = viewModel.color.collectAsState()
    val startTime = viewModel.startTime.collectAsState()
    val endTime = viewModel.endTime.collectAsState()

    val sheetState = rememberModalBottomSheetState()
    var timeState = rememberTimePickerState(
        LocalTime.parse(startTime.value).hour,
        LocalTime.parse(startTime.value).minute,
        true
    )

    Scaffold(modifier = modifier) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {

            InputCard(
                modifier = Modifier.padding(16.dp)
            )

            ColorCard(modifier = Modifier.padding(16.dp)) {
                showBottomSheet = true
                showDaySelector = false
                showColorSelector = true
            }

            OptionCard(
                title = "Day",
                selectionTitle = viewModel.possibleDays[dayIndex.value],
                modifier = Modifier.padding(16.dp),
                onClick = {
                    showBottomSheet = true
                    showDaySelector = true
                    showColorSelector = false
                }
            )

            DoubleOptionCard(
                title = "Time",
                selectionTitleLeft = startTime.value,
                selectionTitleRight = endTime.value,
                modifier = Modifier.padding(16.dp),
                onClickLeft = {
                    showTimePicker = true
                    startTimeTrigger = true
                    endTimeTrigger = false
                },
                onClickRight = {
                    showTimePicker = true
                    endTimeTrigger = true
                    startTimeTrigger = false
                }
            )
        }
    }


    if (showTimePicker) {
        showBottomSheet = false
        TimePickerDialog(
            onCancel = {
                showTimePicker = false
                startTimeTrigger = false
                endTimeTrigger = false
            },
            onConfirm = {
                if (startTimeTrigger) {
                    viewModel.updateStartTime(LocalTime.of(timeState.hour, timeState.minute))
                    //timeState = rememberTimePickerState(1,1,true)
                } else if (endTimeTrigger) {
                    viewModel.updateEndTime(LocalTime.of(timeState.hour, timeState.minute))
                }

                showTimePicker = false
                startTimeTrigger = false
                endTimeTrigger = false
            }) {
            TimePicker(state = timeState)
        }
    }

    if (showBottomSheet) {
        showTimePicker = false
        BottomSheet(
            onDismiss = {
                showBottomSheet = false
                showDaySelector = false
                showColorSelector = false
            },
            content = { onDismiss ->
                if (showDaySelector) {
                    Column(
                        modifier = Modifier
                            .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            modifier = Modifier.padding(bottom = 16.dp),
                            text = "Select a day",
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.titleLarge
                        )
                        repeat(7) { index ->
                            TextButton(
                                modifier = Modifier.fillMaxWidth(),
                                content = {
                                    Text(
                                        text = viewModel.possibleDays[index],
                                        textAlign = TextAlign.Center,
                                        style = MaterialTheme.typography.titleMedium
                                    )
                                },
                                onClick = {
                                    viewModel.updateDay(index)
                                    onDismiss()
                                }
                            )
                        }
                    }
                    //TODO: Proper color picker
                } else if (showColorSelector) {
                    Column() {
                        Text(
                            modifier = Modifier.padding(bottom = 16.dp),
                            text = "Select a color",
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.titleLarge
                        )
                        Row() {
                            Box(
                                modifier = Modifier
                                    .background(Color.Red)
                                    .clip(RoundedCornerShape(12.dp))
                                    .height(40.dp)
                                    .width(40.dp)
                                    .clickable {
                                        viewModel.updateColor(Color.Red.value)
                                        onDismiss()
                                    },
                            ) { }
                            Box(
                                modifier = Modifier
                                    .background(Color.Blue)
                                    .clip(RoundedCornerShape(12.dp))
                                    .height(40.dp)
                                    .width(40.dp)
                                    .clickable {
                                        viewModel.updateColor(Color.Blue.value)
                                        onDismiss()
                                    },
                            ) { }
                        }
                    }
                }
            })
    }

}


@Preview
@Composable
fun NewTaskScreenPreview() {
    NewTaskScreen(
        openAndPopUp = { route, popUp -> }
    )
}