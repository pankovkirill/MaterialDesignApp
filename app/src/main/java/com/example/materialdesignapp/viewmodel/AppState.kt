package com.example.materialdesignapp.viewmodel

import com.example.materialdesignapp.model.PODServerResponseData

sealed class AppState {
    class Success(val serverResponseData: PODServerResponseData) : AppState()
    data class Error(val error: Throwable) : AppState()
    object Loading : AppState()
}