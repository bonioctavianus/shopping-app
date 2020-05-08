package com.bonioctavianus.android.shopping_app.utils

import android.view.View
import android.widget.ImageView

fun View.makeVisible() {
    visibility = View.VISIBLE
}

fun View.makeInvisible() {
    visibility = View.INVISIBLE
}

fun View.makeGone() {
    visibility = View.GONE
}

fun ImageView.loadImage(url: String?) {
    loadImage(context, url, this)
}