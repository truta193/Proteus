package com.truta.proteus_android

sealed class Routes(
    val route: String
) {
    data object SignInScreen : Routes("SignInScreen")
    data object SignUpScreen : Routes("SignUpScreen")
    data object ScheduleScreen : Routes("ScheduleScreen")
}