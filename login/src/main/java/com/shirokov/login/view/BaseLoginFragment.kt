package com.shirokov.login.view

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.NavUtils
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import com.shirokov.login.R
import com.shirokov.login.presenter.LoginPresenter
import com.shirokov.login.presenter.LoginState
import javax.inject.Inject

abstract class BaseLoginFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val viewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)

        if (!viewModel.isInitialized()) {
            injectViewModel(viewModel)
            viewModel.loginPresenter.render = { newState ->
                viewModel.viewState.value = newState
            }
        }

        val loginView = LoginView(this)
        loginView.login = viewModel.loginPresenter::login
        loginView.forgotPassword = viewModel.loginPresenter::forgotPassword

        viewModel.viewState.observe(this, Observer<LoginState> { t ->
            t?.let { loginView.render(it) }
        })

    }

    abstract fun injectViewModel(loginViewModel: LoginViewModel)
}

class LoginViewModel : ViewModel() {
    @Inject
    lateinit var loginPresenter: LoginPresenter
    val viewState = MutableLiveData<LoginState>()

    fun isInitialized(): Boolean {
        return this::loginPresenter.isInitialized
    }

}