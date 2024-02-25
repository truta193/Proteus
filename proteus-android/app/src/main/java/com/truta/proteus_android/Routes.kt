package com.truta.proteus_android

sealed class Routes(
    val route: String
) {
    object SignInScreen : Routes("SignInScreen")
    object SignUpScreen : Routes("SignUpScreen")
}