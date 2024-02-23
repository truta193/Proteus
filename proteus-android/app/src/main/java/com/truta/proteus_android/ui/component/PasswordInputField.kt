package com.truta.proteus_android.ui.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import com.truta.proteus_android.R

@Composable
fun PasswordInputField(
    labelValue: String,
    icon: ImageVector,
    keyboardOptions: KeyboardOptions,
    modifier: Modifier = Modifier,
    errorStatus: Boolean = false,
    errorMessage: String = "",
    onTextSelected: (String) -> Unit
) {

    val localFocusManager = LocalFocusManager.current
    val password = rememberSaveable {
        mutableStateOf("")
    }

    val passwordVisible = rememberSaveable {
        mutableStateOf(false)
    }

    OutlinedTextField(
        modifier = modifier
            .fillMaxWidth(),
        label = { Text(text = labelValue) },
        keyboardOptions = keyboardOptions,
        singleLine = true,
        maxLines = 1,
        value = password.value,
        onValueChange = {
            password.value = it
            onTextSelected(it)
        },
        leadingIcon = {
            Icon(icon, contentDescription = "")
        },
        trailingIcon = {

            val iconImage = if (passwordVisible.value) {
                R.drawable.ic_visibility_off
            } else {
                R.drawable.ic_visibility
            }

            val description = if (passwordVisible.value) {
                "Hide Password"
            } else {
                "Show Password"
            }

            IconButton(onClick = { passwordVisible.value = !passwordVisible.value }) {
                Icon(painterResource(id = iconImage), contentDescription = description)
            }

        },
        visualTransformation = if (passwordVisible.value) VisualTransformation.None else PasswordVisualTransformation(),
        isError = errorStatus,
        supportingText = {
            if (errorStatus) {
                Text(
                    text = errorMessage,
                    modifier = Modifier.fillMaxWidth(),
                    color = MaterialTheme.colorScheme.error
                )
            }
        }
    )
}