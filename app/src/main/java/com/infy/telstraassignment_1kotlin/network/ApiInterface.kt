package com.infy.telstraassignment_1kotlin.network

import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Url


interface ApiInterface {

    @GET
    fun getAPI(@Url url: String): Call<ResponseBody>

}