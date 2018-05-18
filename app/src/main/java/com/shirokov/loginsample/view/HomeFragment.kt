package com.shirokov.loginsample.view

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.shirokov.login.util.ext.toolbarTitle
import com.shirokov.login.util.ext.transaction
import com.shirokov.login.view.LoginCallback
import com.shirokov.loginsample.R
import com.shirokov.loginsample.model.CityWeather
import com.shirokov.loginsample.service.WeatherService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class HomeFragment : Fragment(), ToolbarTitle, LoginCallback {
    override var title: Int = R.string.main_screen_title

    override fun onResult() {
        WeatherService.getInstance().getWeather("Smolensk", getString(R.string.weather_api_key))
                .enqueue(object: Callback<CityWeather>{
                    override fun onFailure(call: Call<CityWeather>?, t: Throwable?) {
                    }

                    override fun onResponse(call: Call<CityWeather>?, response: Response<CityWeather>?) {
                        view?.let {
                            Snackbar.make(it, response?.body()!!.toString(), Snackbar.LENGTH_LONG)
                                    .setAction("CLOSE") {

                                    }
                                    .show()
                        }
                    }

                })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        toolbarTitle = getString(title)

        view?.findViewById<View>(R.id.login_button)?.setOnClickListener {
            fragmentManager?.transaction {
                val loginFragment = LoginFragment()
                loginFragment.setTargetFragment(this@HomeFragment, -1)
                add(R.id.fragment_container, loginFragment)
                addToBackStack(null)
            }
        }
    }
}