package com.truta.proteus_android.ui.screen.schedule

import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.truta.proteus_android.ui.screen.sign_in.SignInViewModel

@Composable
fun ScheduleScreen(
    modifier: Modifier = Modifier,
    openAndPopUp: (String, String) -> Unit,
    viewModel: SignInViewModel = hiltViewModel()
) {
    Surface {
        Text(text = "Schedule Screen")
    }
}