package com.truta.proteus_android.ui.screen.sign_in

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.truta.proteus_android.ui.component.PasswordInputField
import com.truta.proteus_android.ui.component.TextInputField
import com.truta.proteus_android.ui.screen.sign_up.SignUpViewModel

@Composable
fun SignInScreen(
    modifier: Modifier = Modifier,
    viewModel: SignInViewModel = hiltViewModel()
) {
    val emailError = viewModel.emailError.collectAsState()
    val passwordError = viewModel.passwordError.collectAsState()
    val signUpEnabled = viewModel.signInEnabled.collectAsState()

    Surface(
        modifier = modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .imePadding()
                .verticalScroll(rememberScrollState())
                .statusBarsPadding()
                .safeDrawingPadding()
                .padding(32.dp)
        ) {
            TextInputField(
                labelValue = "Email",
                icon = Icons.Rounded.Email,
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                onTextChanged = { viewModel.updateEmail(it) },
                errorStatus =  emailError.value,
                errorMessage = "Email must be valid (e.g. name@domain.com)"
            )

            PasswordInputField(
                labelValue = "Password",
                icon = Icons.Rounded.Lock,
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Next
                ),
                onTextSelected = { viewModel.updatePassword(it) },
                errorStatus = passwordError.value,
                errorMessage = "Password must be at least 8 characters long"
            )

            Button(
                contentPadding = PaddingValues(),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
                    .height(48.dp),
                enabled = signUpEnabled.value,
                onClick = { viewModel.onSignInClick({_,_ ->}) }
            ) {
                Text(text = "Sign In")
            }
        }
    }
}