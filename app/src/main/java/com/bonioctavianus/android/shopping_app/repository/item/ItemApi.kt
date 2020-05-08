package com.bonioctavianus.android.shopping_app.repository.item

import io.reactivex.Single
import retrofit2.http.GET

interface ItemApi {

    @GET(value = "/home")
    fun getHomeItems(): Single<List<HomeResponse>>

}