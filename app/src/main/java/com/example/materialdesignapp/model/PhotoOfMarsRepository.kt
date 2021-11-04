package com.example.materialdesignapp.model

import retrofit2.Callback

interface PhotoOfMarsRepository {
    fun getData(callback: Callback<POMServerResponseData>)
}