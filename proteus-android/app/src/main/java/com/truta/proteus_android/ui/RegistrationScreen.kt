package com.truta.proteus_android.ui

import android.provider.CalendarContract
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imeNestedScroll
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.truta.proteus_android.domain.RegistrationViewModel
import com.truta.proteus_android.ui.components.InputField
import com.truta.proteus_android.ui.components.PasswordInputField

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun RegistrationScreen() {

    val viewModel: RegistrationViewModel = hiltViewModel()

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        color = Color.Transparent
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .imePadding()
                .imeNestedScroll(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            InputField(labelValue = "Email", icon = Icons.Rounded.Email, errorStatus = viewModel.uiState.value.emailError) {
                viewModel.onEvent(RegistrationEvent.EmailChanged(it))
            }

            PasswordInputField(labelValue = "Password", icon = Icons.Rounded.Lock , errorStatus = viewModel.uiState.value.passwordError) {
                viewModel.onEvent(RegistrationEvent.PasswordChanged(it))
            }

            PasswordInputField(labelValue = "Confirm Password", icon = Icons.Rounded.Lock , errorStatus = viewModel.uiState.value.confirmPasswordError) {
                viewModel.onEvent(RegistrationEvent.ConfirmPasswordChanged(it))
            }

            Button(
                contentPadding = PaddingValues(),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
                    .height(48.dp),

                onClick = {
                    viewModel.onEvent(RegistrationEvent.RegisterButtonClicked)
                }
            ) {
                Text(text = "Register")
            }
        }
    }
}

@Preview
@Composable
fun RegistrationScreenPreview() {
    RegistrationScreen()
}