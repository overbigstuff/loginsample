package com.shirokov.loginsample.view

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import com.shirokov.login.model.LoginModel
import com.shirokov.login.model.LoginRepository
import com.shirokov.login.model.LoginResult
import com.shirokov.login.presenter.LoginPresenter
import com.shirokov.login.presenter.LoginRouter

import com.shirokov.login.util.ext.toolbarTitle
import com.shirokov.login.view.BaseLoginFragment
import com.shirokov.login.view.LoginCallback
import com.shirokov.login.view.LoginViewModel
import com.shirokov.loginsample.MainActivity
import com.shirokov.loginsample.R
import com.shirokov.loginsample.util.hideKeyboard
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class LoginFragment : BaseLoginFragment(), ToolbarTitle {
    override var title: Int = R.string.login_screen_title

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        toolbarTitle = getString(R.string.login_screen_title)

        (activity as? MainActivity)?.showBackArrow(true)
    }

    override fun injectViewModel(loginViewModel: LoginViewModel) {
        val loginRepository = object : LoginRepository {
            override fun login(email: String, password: String): Observable<LoginResult> {
                activity?.hideKeyboard()
                return Observable.just(LoginResult(success = true))
                        .delay(1000, TimeUnit.MILLISECONDS)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
            }

        }

        val loginRouter = object : LoginRouter {

            override fun returnToHomeScreen() {
                (targetFragment as? LoginCallback)?.onResult()
                activity?.onBackPressed()
            }

            override fun forgotPassword() {

            }

        }

        val loginModel = LoginModel(loginRepository)

        loginViewModel.loginPresenter = LoginPresenter(loginModel, loginRouter)
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.menu_fragment_login, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            R.id.action_create -> {

            }
        }

        return super.onOptionsItemSelected(item)
    }
}