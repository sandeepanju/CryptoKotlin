package com.example.cryptokotlin.utils

import android.app.Activity
import android.content.Context
import android.view.inputmethod.InputMethodManager

fun hideKeyboard(context: Context) {
    val inputManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    // check if no view has focus:
    val v = (context as Activity).currentFocus ?: return
    inputManager.hideSoftInputFromWindow(v.windowToken, 0)
}
