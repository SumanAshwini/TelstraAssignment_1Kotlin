package com.infy.telstraassignment_1kotlin.network

import com.infy.telstraassignment_1kotlin.general.AppConstants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object ApiClient {

    private val API_BASE_URL = AppConstants.domain


    private val builder = Retrofit.Builder()
        .baseUrl(API_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())

    val client = builder.build()

}