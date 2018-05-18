package com.shirokov.login.model

import android.util.Patterns

class LoginModel(private val loginRepository: LoginRepository) {
    fun login(email: String, password: String, success: () -> Unit, error: () -> Unit) {
        loginRepository.login(email, password).subscribe(
                {
                    success()
                },
                {
                    error()
                }
        )
    }

    fun isEmailValid(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun isPasswordValid(password: String): Boolean {
        return password.length >= 6
                && password.map { it.isDigit() }.reduce { acc, b -> acc || b }
                && password.toLowerCase() != password && password.toUpperCase() != password
    }
}