package com.example.materialdesignapp.model

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PictureOfTheDayAPI {
    @GET("planetary/apod")
    fun getPictureOfTheDay(
        @Query("api_key") api_key: String
    ): Call<PODServerResponseData>
}