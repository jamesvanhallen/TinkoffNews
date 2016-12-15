package com.james.tinkoffnews

import android.app.Activity
import android.content.Context.CONNECTIVITY_SERVICE
import android.content.Context.INPUT_METHOD_SERVICE
import android.graphics.Color
import android.net.ConnectivityManager
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView



fun View.show() {
    this.visibility = View.VISIBLE
}

fun View.hide() {
    this.visibility = View.GONE
}

fun View.invisible() {
    this.visibility = View.INVISIBLE
}

fun Activity.hideKeyboard() {
    val inputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(currentFocus.windowToken, 0)
}

fun Activity.isOnline(): Boolean {
    val cm = this.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
    val netInfo = cm.activeNetworkInfo

    return netInfo != null && netInfo.isConnectedOrConnecting
}

fun Snackbar.setWhiteText() {
    (view.findViewById(android.support.design.R.id.snackbar_text) as TextView).setTextColor(Color.WHITE)
}

fun AppCompatActivity.addFragment(f: Fragment) {
    initTransaction(f, true)
}

fun AppCompatActivity.replaceFragment(f: Fragment) {
    initTransaction(f, false)
}

private fun AppCompatActivity.initTransaction(f: Fragment, addToBackStack: Boolean) {
    val fragTransaction = supportFragmentManager.beginTransaction()
            .replace(R.id.main_container, f)

    if (addToBackStack) fragTransaction.addToBackStack(f.javaClass.simpleName)

    Log.wtf("TRRR", "add frag $f.javaClass.simpleName")

    fragTransaction.commit()
}