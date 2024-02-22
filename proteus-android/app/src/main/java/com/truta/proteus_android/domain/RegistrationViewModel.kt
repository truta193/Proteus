package com.truta.proteus_android.domain

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.truta.proteus_android.domain.rules.RegistrationValidator
import com.truta.proteus_android.domain.service.IAuthenticationService
import com.truta.proteus_android.ui.RegistrationEvent
import com.truta.proteus_android.ui.RegistrationState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val authenticationService: IAuthenticationService
) : ViewModel() {
    var uiState = mutableStateOf(RegistrationState())

    fun onEvent(event: RegistrationEvent) {
        uiState.value = uiState.value.copy(
            initialState = false
        )

        when (event) {
            is RegistrationEvent.EmailChanged -> {
                uiState.value = uiState.value.copy(
                    email = event.email
                )
                validateUi()
            }
            is RegistrationEvent.PasswordChanged -> {
                uiState.value = uiState.value.copy(
                    password = event.password
                )
                validateUi()
            }
            is RegistrationEvent.ConfirmPasswordChanged -> {
                uiState.value = uiState.value.copy(
                    confirmPassword = event.confirmPassword
                )
                validateUi()
            }
            is RegistrationEvent.RegisterButtonClicked -> {
                signUp()
            }
            else -> {

            }
        }
    }

    fun validateUi() {
        uiState.value = uiState.value.copy(
            emailError = !RegistrationValidator.validateEmail(uiState.value.email),
            passwordError = !RegistrationValidator.validatePassword(uiState.value.password),
            confirmPasswordError = !RegistrationValidator.validateConfirmPassword(
                uiState.value.password,
                uiState.value.confirmPassword
            )
        )
    }

    fun signUp() {
        if (RegistrationValidator.validateForm(
                uiState.value.email,
                uiState.value.password,
                uiState.value.confirmPassword
            )
        ) {
            authenticationService.signUp(uiState.value.email, uiState.value.password) { _,str ->
                Log.d("EVENT_TEST", "SIGN UP SUCCESS $str")
            }
        } else {
            Log.d("EVENT_TEST", "SIGN UP FAILED")
        }
    }

}