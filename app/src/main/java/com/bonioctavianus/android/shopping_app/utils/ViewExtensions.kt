package com.bonioctavianus.android.shopping_app.utils

import android.view.View
import android.widget.ImageView
import android.widget.Toast

fun View.makeVisible() {
    visibility = View.VISIBLE
}

fun View.makeInvisible() {
    visibility = View.INVISIBLE
}

fun View.makeGone() {
    visibility = View.GONE
}

fun View.showToast(message: String?) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}

fun ImageView.loadImage(url: String?) {
    loadImage(context, url, this)
}