package com.example.materialdesignapp.model

import retrofit2.Callback

interface Repository {
    fun getData(date: String, callback: Callback<PODServerResponseData>)
}