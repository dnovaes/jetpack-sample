package com.arctouch.io.outdoorsychallenge.extensions

import android.content.Context
import android.view.inputmethod.InputMethodManager

val Context.inputMethodService
    get() = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
