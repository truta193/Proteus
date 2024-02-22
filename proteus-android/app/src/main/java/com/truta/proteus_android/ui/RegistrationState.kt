package com.truta.proteus_android.ui

data class RegistrationState(
    var email: String = "",
    var password: String = "",
    var confirmPassword: String = "",

    var emailError: Boolean = false,
    var passwordError: Boolean = false,
    var confirmPasswordError: Boolean = false,

    var initialState: Boolean = true

)