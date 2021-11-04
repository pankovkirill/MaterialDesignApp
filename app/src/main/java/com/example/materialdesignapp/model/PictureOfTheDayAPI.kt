package com.example.materialdesignapp.model

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PictureOfTheDayAPI {
    @GET("planetary/apod?")
    fun getPictureOfTheDay(
        @Query("date") date: String,
        @Query("api_key") api_key: String
    ): Call<PODServerResponseData>

    @GET("mars-photos/api/v1/rovers/curiosity/photos?")
    fun getPhotoOfMars(
        @Query("sol") sol: Int,
        @Query("page") page: Int,
        @Query("api_key") api_key: String
    ): Call<POMServerResponseData>
}