package com.truta.proteus_android.ui.screen.sign_in

import com.truta.proteus_android.rules.FormValidator
import com.truta.proteus_android.service.AuthenticationService
import com.truta.proteus_android.ui.screen.AppViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val authenticationService: AuthenticationService
): AppViewModel() {
    val email = MutableStateFlow("")
    val password = MutableStateFlow("")
    val emailError = MutableStateFlow(false)
    val passwordError = MutableStateFlow(false)
    val signInEnabled = MutableStateFlow(false)

    fun updateEmail(newEmail: String) {
        email.value = newEmail
        validateUi()
    }

    fun updatePassword(newPassword: String) {
        password.value = newPassword
        validateUi()
    }

    fun validateUi() {
        emailError.value = !FormValidator.validateEmail(email.value)
        passwordError.value = !FormValidator.validatePassword(password.value)
        signInEnabled.value = !emailError.value && !passwordError.value
    }

    fun onSignInClick(openAndPopUp: (String, String) -> Unit) {
        launchCatching {
            if (!FormValidator.validateForm(email.value, password.value)){
                throw Exception("Invalid email or password")
            }

            authenticationService.signIn(email.value, password.value)
            //openAndPopUp("notes_list_screen", "sign_up_screen")
        }
    }
}