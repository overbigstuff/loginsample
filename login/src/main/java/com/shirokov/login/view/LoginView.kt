package com.shirokov.login.view

import android.support.design.widget.TextInputEditText
import android.support.design.widget.TextInputLayout
import android.view.View
import android.widget.EditText
import com.shirokov.login.R
import com.shirokov.login.presenter.InProgressState
import com.shirokov.login.presenter.LoginState
import com.shirokov.login.presenter.WrongState


class LoginView(private val loginFragment: BaseLoginFragment) {
    private val passwordFieldLayout = loginFragment.view?.findViewById<TextInputLayout>(R.id.password_input_layout)
    private val emailFieldLayout = loginFragment.view?.findViewById<TextInputLayout>(R.id.email_input_layout)
    private val progressOverlay = loginFragment.view?.findViewById<View>(R.id.progress_overlay)
    private val loginButton = loginFragment.view?.findViewById<View>(R.id.login_button)

    var login: (email: String, password: String) -> Unit = { _, _ -> }
    var forgotPassword: () -> Unit = { }

    init {
        val forgotPassword = loginFragment.view?.findViewById<View>(R.id.forgot_password)
        val passwordField = loginFragment.view?.findViewById<EditText>(R.id.password_field)
        val emailField = loginFragment.view?.findViewById<EditText>(R.id.email_field)

        loginButton?.setOnClickListener {
            val password: String = passwordField?.text?.toString() ?: ""
            val email: String = emailField?.text?.toString() ?: ""
            login(email, password)
        }

        forgotPassword?.setOnClickListener {
            forgotPassword()
        }
    }

    fun render(loginState: LoginState) {
        when (loginState) {
            is WrongState ->
                wrongState(loginState.wrongLogin, loginState.wrongPassword)
            is InProgressState -> inProgress()
            is UnknownError -> unknownError()
        }
    }

    private fun wrongState(isEmailWrong: Boolean, isPasswordWrong: Boolean) {
        loginButton?.isEnabled = true
        progressOverlay?.visibility = View.GONE

        if (isEmailWrong) {
            emailFieldLayout?.error = loginFragment.getString(R.string.wrong_email)
        }

        emailFieldLayout?.isErrorEnabled = isEmailWrong

        if (isPasswordWrong) {
            passwordFieldLayout?.error = loginFragment.getString(R.string.wrong_password)
        }

        passwordFieldLayout?.isErrorEnabled = isPasswordWrong

    }

    private fun unknownError() {
        progressOverlay?.visibility = View.GONE
        loginButton?.isEnabled = true
    }

    private fun inProgress() {
        progressOverlay?.visibility = View.VISIBLE
        loginButton?.isEnabled = false
    }
}