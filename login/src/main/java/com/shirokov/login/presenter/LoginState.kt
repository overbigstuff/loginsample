package com.shirokov.login.presenter

sealed class LoginState

class WrongState(val wrongLogin: Boolean, val wrongPassword: Boolean = false): LoginState()
class UnknownError: LoginState()
class InProgressState: LoginState()