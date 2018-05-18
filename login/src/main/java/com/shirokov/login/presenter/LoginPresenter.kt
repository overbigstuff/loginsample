package com.shirokov.login.presenter

import com.shirokov.login.model.LoginModel

class LoginPresenter(private var loginModel: LoginModel, private var loginRouter: LoginRouter) {

    var render: (LoginState) -> Unit = {}

    fun login(email: String, password: String) {
        val wrongEmail = !loginModel.isEmailValid(email)
        val wrongPassword = !loginModel.isPasswordValid(password)

        if (wrongEmail || wrongPassword) {
            render(WrongState(wrongEmail, wrongPassword))
            return
        }

        render(InProgressState())

        loginModel.login(email, password,
                {
                    loginRouter.returnToHomeScreen()
                },
                {
                    render(UnknownError())
                })
    }

    fun forgotPassword() {
        loginRouter.forgotPassword()
    }
}