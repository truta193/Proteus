package com.truta.proteus_android.ui.screen.sign_up

import android.util.Log
import com.truta.proteus_android.rules.FormValidator
import com.truta.proteus_android.service.AuthenticationService
import com.truta.proteus_android.ui.screen.AppViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val authenticationService: AuthenticationService
): AppViewModel() {
    val email = MutableStateFlow("")
    val password = MutableStateFlow("")
    val confirmPassword = MutableStateFlow("")
    val emailError = MutableStateFlow(false)
    val passwordError = MutableStateFlow(false)
    val confirmPasswordError = MutableStateFlow(false)
    val signUpEnabled = MutableStateFlow(false)

    fun updateEmail(newEmail: String) {
        email.value = newEmail
        validateUi()
    }

    fun updatePassword(newPassword: String) {
        password.value = newPassword
        validateUi()
    }

    fun updateConfirmPassword(newConfirmPassword: String) {
        confirmPassword.value = newConfirmPassword
        validateUi()
    }

    fun validateUi() {
        emailError.value = !FormValidator.validateEmail(email.value)
        passwordError.value = !FormValidator.validatePassword(password.value)
        confirmPasswordError.value = !FormValidator.validateConfirmPassword(password.value, confirmPassword.value)
        signUpEnabled.value = !emailError.value && !passwordError.value && !confirmPasswordError.value
    }

    fun onSignUpClick(openAndPopUp: (String, String) -> Unit) {
        launchCatching (
            block = {
                if (!FormValidator.validateForm(
                        email.value,
                        password.value,
                        confirmPassword.value
                    )
                ) {
                    Log.d("SignUpViewModel", "throwing exception")
                    throw Exception("Passwords do not match")
                }

                authenticationService.signUp(email.value, password.value)
                //openAndPopUp(NOTES_LIST_SCREEN, SIGN_UP_SCREEN)
            }
        )
    }
}