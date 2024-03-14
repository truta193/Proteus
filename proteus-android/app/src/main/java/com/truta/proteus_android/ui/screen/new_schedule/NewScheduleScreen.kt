package com.truta.proteus_android.ui.screen.new_schedule

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Create
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.truta.proteus_android.ui.component.BottomSheet
import com.truta.proteus_android.ui.component.TextInputField

@Composable
fun NewScheduleScreen(
    modifier: Modifier = Modifier,
    viewModel: NewScheduleViewModel = hiltViewModel(),
    onDismiss: () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()

    val titleError = viewModel.titleError.collectAsState()
    val isFormValid = viewModel.isFormValid.collectAsState()

    BottomSheet(
        modifier = modifier,
        coroutineScope = coroutineScope,
        onDismiss = onDismiss,
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
                        modifier = Modifier.padding(bottom = 16.dp),
                        text = "Add a new schedule",
                        style = MaterialTheme.typography.titleLarge
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Button(onClick = {
                        viewModel.addSchedule()
                        dismissSheet()
                    },
                        enabled = isFormValid.value
                    ) {
                        Text(text = "Add")
                    }
                }


                Column() {
                    TextInputField(
                        labelValue = "Title",
                        icon = Icons.Rounded.Create,
                        keyboardOptions = KeyboardOptions.Default.copy(
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Done
                        ),
                        onTextChanged = { viewModel.updateTitle(it) },
                        errorStatus = titleError.value,
                        errorMessage = "Title cannot be empty."
                    )
                }
            }
        }
    )
}