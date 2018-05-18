package com.shirokov.loginsample.util

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager


fun Activity.hideKeyboard() {
    val view = findViewById<View>(android.R.id.content)
    if (view != null) {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0)
    }
}