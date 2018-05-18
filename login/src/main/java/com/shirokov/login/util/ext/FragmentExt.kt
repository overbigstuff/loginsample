package com.shirokov.login.util.ext

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity


fun FragmentManager.transaction(block: FragmentTransaction.() -> Unit) {
    val transaction = beginTransaction()
    transaction.block()
    transaction.commit()
}

var Fragment.toolbarTitle: CharSequence
        set (value) {
            (activity as? AppCompatActivity)?.supportActionBar?.title = value
        }
        get() {
            val title = (activity as? AppCompatActivity)?.supportActionBar?.title
            return title ?: ""
        }