package com.truta.proteus_android.ui.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun TextInputField(
    labelValue: String,
    icon: ImageVector,
    keyboardOptions: KeyboardOptions,
    modifier: Modifier = Modifier,
    errorStatus: Boolean = false,
    errorMessage: String = "",
    onTextChanged: (String) -> Unit
) {
    val textValue = rememberSaveable {
        mutableStateOf("")
    }

    OutlinedTextField(
        modifier = modifier
            .fillMaxWidth(),
        label = { Text(text = labelValue) },
        keyboardOptions = keyboardOptions,
        singleLine = true,
        maxLines = 1,
        value = textValue.value,
        onValueChange = {
            textValue.value = it
            onTextChanged(it)
        },
        leadingIcon = {
            Icon(icon, "")
        },
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

@Preview
@Composable
fun InputFieldPreview() {
    TextInputField(
        labelValue = "Email",
        icon = Icons.Rounded.Email,
        errorStatus = false,
        errorMessage = "",
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Next
        ),
        onTextChanged = {}
    )
}