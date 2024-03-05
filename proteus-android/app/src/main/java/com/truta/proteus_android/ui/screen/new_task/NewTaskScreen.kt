package com.truta.proteus_android.ui.screen.new_task

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.truta.proteus_android.ui.component.OptionCard
import kotlinx.coroutines.launch
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewTaskScreen(
    openAndPopUp: (String, String) -> Unit,
    modifier: Modifier = Modifier
) {
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    var showBottomSheet by rememberSaveable { mutableStateOf(false) }

    var dayIndex by rememberSaveable { mutableIntStateOf(LocalDate.now().dayOfWeek.value - 1) }

    var possibleDays =
        listOf("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday")

    Scaffold(modifier = modifier) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            OptionCard(
                title = "Day",
                selectionTitle = possibleDays[dayIndex],
                modifier = Modifier.padding(16.dp),
                onClick = { showBottomSheet = true }
            )
        }
        if (showBottomSheet) {
            ModalBottomSheet(
                onDismissRequest = {
                    showBottomSheet = false
                },
                sheetState = sheetState
            ) {
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
                                    text = possibleDays[index],
                                    textAlign = TextAlign.Center,
                                    style = MaterialTheme.typography.titleMedium
                                )

                            },
                            onClick = {
                                dayIndex = index
                                scope.launch { sheetState.hide() }.invokeOnCompletion {
                                    if (!sheetState.isVisible) {
                                        showBottomSheet = false
                                    }
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun NewTaskScreenPreview() {
    NewTaskScreen(
        openAndPopUp = { route, popUp -> }
    )
}