package com.truta.proteus_android.rules

object FormValidator {
    fun validateEmail(email: String): Boolean {
        val emailRegex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}\$".toRegex()
        return emailRegex.matches(email)
    }

    fun validatePassword(password: String): Boolean {
        return password.length >= 8
    }

    fun validateConfirmPassword(password: String, confirmPassword: String): Boolean {
        if (confirmPassword.isEmpty()  || confirmPassword.length < 8) {
            return false
        }
        return password == confirmPassword
    }

    fun validateForm(email: String, password: String, confirmPassword: String): Boolean {
        return validateEmail(email) && validatePassword(password) && validateConfirmPassword(password, confirmPassword)
    }

    fun validateForm(email: String, password: String): Boolean {
        return validateEmail(email) && validatePassword(password)
    }
}