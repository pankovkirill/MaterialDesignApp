package com.example.materialdesignapp.viewmodel

import com.example.materialdesignapp.model.PODServerResponseData
import com.example.materialdesignapp.model.POMServerResponseData

sealed class AppState {
    class Success(val serverResponseData: PODServerResponseData) : AppState()
    class SuccessPhoto(val serverResponseData: POMServerResponseData) : AppState()
    data class Error(val error: Throwable) : AppState()
    object Loading : AppState()
}