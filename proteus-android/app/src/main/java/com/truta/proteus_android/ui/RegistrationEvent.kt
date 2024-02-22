package com.truta.proteus_android.ui

sealed class RegistrationEvent {
    data class EmailChanged(val email: String) : RegistrationEvent()
    data class PasswordChanged(val password: String) : RegistrationEvent()
    data class ConfirmPasswordChanged(val confirmPassword: String) : RegistrationEvent()

    object RegisterButtonClicked : RegistrationEvent()
}