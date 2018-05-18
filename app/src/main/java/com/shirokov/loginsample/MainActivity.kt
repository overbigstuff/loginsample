package com.shirokov.loginsample

import android.graphics.PorterDuff
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.Toolbar
import com.shirokov.login.util.ext.transaction
import com.shirokov.loginsample.util.hideKeyboard
import com.shirokov.loginsample.view.HomeFragment
import com.shirokov.loginsample.view.ToolbarTitle

class MainActivity : AppCompatActivity() {
    var toolbar: Toolbar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        toolbar?.setNavigationOnClickListener {
            hideKeyboard()
            onBackPressed()
        }

        if (supportFragmentManager.findFragmentById(R.id.fragment_container) == null) {
            supportFragmentManager.transaction {
                replace(R.id.fragment_container, HomeFragment())
            }
        }

        supportFragmentManager.addOnBackStackChangedListener {
            val fragment = supportFragmentManager.findFragmentById(R.id.fragment_container)
            if (fragment != null && fragment is ToolbarTitle) {
                supportActionBar?.title = getString(fragment.title)
            }
            if (supportFragmentManager.backStackEntryCount > 0) {
                showBackArrow(true)
            } else {
                showBackArrow(false)
            }
        }
    }

    fun showBackArrow(isShown: Boolean) {
        if (isShown) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            toolbar.navigationIconColor(ContextCompat.getColor(this, R.color.colorAccent))
        } else {
            supportActionBar?.setDisplayHomeAsUpEnabled(false)
        }
    }

}

fun Toolbar?.navigationIconColor(color: Int) {
    this?.navigationIcon?.setColorFilter(color, PorterDuff.Mode.SRC_ATOP)
}
