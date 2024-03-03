package com.truta.proteus_android

import ProteusAppState
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.truta.proteus_android.ui.screen.schedule.ScheduleScreen
import com.truta.proteus_android.ui.screen.sign_in.SignInScreen
import com.truta.proteus_android.ui.screen.sign_up.SignUpScreen
import com.truta.proteus_android.ui.theme.ProteusAndroidTheme

@Composable
fun ProteusApp() {
    ProteusAndroidTheme {
        Surface(
            color = MaterialTheme.colorScheme.background
        ) {
            val appState = rememberAppState()


                NavHost(
                    navController = appState.navController,
                    startDestination = Routes.SignInScreen.route,
                    //startDestination = Routes.ScheduleScreen.route,
                ) {
                    routeGraph(appState)
                }

        }
    }
}

@Composable
fun rememberAppState(navController: NavHostController = rememberNavController()) =
    remember(navController) {
        ProteusAppState(navController)
    }

fun NavGraphBuilder.routeGraph(appState: ProteusAppState) {
    composable(Routes.SignInScreen.route) {
        SignInScreen(
            openAndPopUp = { route, popUp -> appState.navigateAndPopUp(route, popUp) }
        )
    }

    composable(Routes.SignUpScreen.route) {
        SignUpScreen(
            openAndPopUp = { route, popUp -> appState.navigateAndPopUp(route, popUp) }
        )
    }

    composable(Routes.ScheduleScreen.route) {
        ScheduleScreen(
            openAndPopUp = { route, popUp -> appState.navigateAndPopUp(route, popUp) }
        )
    }
}