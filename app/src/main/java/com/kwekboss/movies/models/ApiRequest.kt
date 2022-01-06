package com.kwekboss.movies.models

import retrofit2.Response
import retrofit2.http.GET

interface ApiRequest{
    @GET(".")
   suspend fun getRequest():Response<List<MoviesItem>>
}