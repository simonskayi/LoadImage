package com.kwekboss.movies.models

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitInstance {
    const val base_URL= "https://simple-node-app-nkd.herokuapp.com"

    val retrofit = Retrofit.Builder()
        .baseUrl(base_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ApiRequest::class.java)
}