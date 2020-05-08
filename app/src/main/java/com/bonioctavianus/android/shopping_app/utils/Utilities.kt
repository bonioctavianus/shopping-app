package com.bonioctavianus.android.shopping_app.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.widget.ImageView
import com.bonioctavianus.android.shopping_app.R
import com.bumptech.glide.Glide

fun hasNetworkConnectivity(context: Context): Boolean? {
    var isConnected: Boolean? = false
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
    if (activeNetwork != null && activeNetwork.isConnected) {
        isConnected = true
    }
    return isConnected
}

fun loadImage(context: Context, url: String?, view: ImageView) {
    Glide.with(context)
        .load(url)
        .centerCrop()
        .placeholder(R.drawable.ic_image_black)
        .error(R.drawable.ic_broken_image_black)
        .into(view)
}