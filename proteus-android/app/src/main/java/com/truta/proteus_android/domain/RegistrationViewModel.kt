package com.truta.proteus_android.domain

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.truta.proteus_android.AppViewModel
import com.truta.proteus_android.domain.rules.RegistrationValidator
import com.truta.proteus_android.domain.service.IAuthenticationService
import com.truta.proteus_android.ui.SignUpEvent
import com.truta.proteus_android.ui.SignUpState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val authenticationService: IAuthenticationService
) : AppViewModel() {
    var uiState = mutableStateOf(SignUpState())

    fun onEvent(event: SignUpEvent) {
        uiState.value = uiState.value.copy(
            initialState = false
        )

        when (event) {
            is SignUpEvent.EmailChanged -> {
                uiState.value = uiState.value.copy(
                    email = event.email
                )
                validateUi()
            }
            is SignUpEvent.PasswordChanged -> {
                uiState.value = uiState.value.copy(
                    password = event.password
                )
                validateUi()
            }
            is SignUpEvent.ConfirmPasswordChanged -> {
                uiState.value = uiState.value.copy(
                    confirmPassword = event.confirmPassword
                )
                validateUi()
            }
            is SignUpEvent.SignUpButtonClicked -> {
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
            launchCatching {
                authenticationService.signUp(uiState.value.email, uiState.value.password)
            }
        } else {
            Log.d("EVENT_TEST", "SIGN UP FAILED")
        }
    }

}