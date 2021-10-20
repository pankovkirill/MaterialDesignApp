package com.example.materialdesignapp.model

import com.example.materialdesignapp.BuildConfig
import com.google.gson.GsonBuilder
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RemoteDataSource {
    private val nasaApi = Retrofit.Builder()
        .baseUrl("https://api.nasa.gov/")
        .addConverterFactory(
            GsonConverterFactory.create(GsonBuilder().setLenient().create())
        )
        .build()
        .create(PictureOfTheDayAPI::class.java)

    fun getDataFromServer(callback: Callback<PODServerResponseData>) {
        nasaApi.getPictureOfTheDay(BuildConfig.API_KEY).enqueue(callback)
    }
}

