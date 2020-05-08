package com.bonioctavianus.android.shopping_app.network

import com.bonioctavianus.android.shopping_app.ShopApplication
import com.bonioctavianus.android.shopping_app.utils.hasNetworkConnectivity
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class HttpRequestInterceptor @Inject constructor(private val application: ShopApplication) :
    Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()

        request = if (hasNetworkConnectivity(application)!!)
            request.newBuilder().header("Cache-Control", "public, max-age=" + 5).build()
        else
            request.newBuilder().header(
                "Cache-Control",
                "public, only if cached, max-stale=" + 60 * 60 * 24 * 7
            ).build()

        val url = request.url.newBuilder().build()
        request = request.newBuilder().url(url).build()
        return chain.proceed(request)
    }
}