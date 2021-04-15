package com.example.ktool.ext

import android.content.res.Resources
import android.util.Log
import android.util.TypedValue
import android.view.View

val Float.dp
    get() = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this,
        Resources.getSystem().displayMetrics
    )

inline val Int.dp
    get() = this.toFloat().dp.toInt()

fun View.isVisible(visible: Boolean) = run {
    visibility = if (visible) {
        View.VISIBLE
    } else {
        View.GONE
    }
}

fun log(message: String) {
    Log.e("😂😂😂", message)
}
