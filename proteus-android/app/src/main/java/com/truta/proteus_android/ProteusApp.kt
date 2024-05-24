package com.truta.proteus_android

import ProteusAppState
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.truta.proteus_android.ui.screen.new_task.NewTaskScreen
import com.truta.proteus_android.ui.screen.schedule.ScheduleScreen
import com.truta.proteus_android.ui.screen.sign_in.SignInScreen
import com.truta.proteus_android.ui.screen.sign_up.SignUpScreen
import com.truta.proteus_android.ui.theme.ProteusAndroidTheme

@Composable
fun ProteusApp() {
    ProteusAndroidTheme {
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            val appState = rememberAppState()

            NavHost(
                navController = appState.navController,
                //startDestination = Routes.NewTaskScreen.route,
                //startDestination = Routes.ScheduleScreen.route,
                startDestination = Routes.SignInScreen.route
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
            restartApp = { route -> appState.clearAndNavigate(route) },
            openScreen = { route -> appState.navigate(route) }
        )
    }

    composable(
        route = Routes.NewTaskScreen.route + "?taskId={taskId}",
        arguments = listOf(navArgument("taskId") { defaultValue = "default" })
    ) {
        NewTaskScreen(
            taskId = it.arguments?.getString("taskId") ?: "default",
            popUpScreen = { appState.popUp() },
            restartApp = { route -> appState.clearAndNavigate(route) }
        )
    }

}