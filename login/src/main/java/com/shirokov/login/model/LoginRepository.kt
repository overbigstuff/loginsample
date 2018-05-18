package com.shirokov.login.model

import io.reactivex.Observable

interface LoginRepository {
    fun login(email: String, password: String) : Observable<LoginResult>
}