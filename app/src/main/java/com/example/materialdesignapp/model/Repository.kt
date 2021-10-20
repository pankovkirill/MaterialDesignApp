package com.example.materialdesignapp.model

import retrofit2.Callback

interface Repository {
    fun getData(callback: Callback<PODServerResponseData>)
}