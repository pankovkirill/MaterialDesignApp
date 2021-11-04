package com.example.materialdesignapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.materialdesignapp.model.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import kotlin.jvm.Throws

private const val SERVER_ERROR = "Ошибка сервера"
private const val REQUEST_ERROR = "Ошибка запроса на сервер"

class PhotoOfMarsViewModel(
    val liveData: MutableLiveData<AppState> = MutableLiveData(),
    private val repository: PhotoOfMarsRepositoryImpl = PhotoOfMarsRepositoryImpl(RemoteDataSource())
) : ViewModel() {

    fun getData() {
        liveData.value = AppState.Loading
        repository.getData(callback)
    }

    private val callback = object : Callback<POMServerResponseData> {
        @Throws(IOException::class)
        override fun onResponse(
            call: Call<POMServerResponseData>,
            response: Response<POMServerResponseData>
        ) {
            val serverResponse: POMServerResponseData? = response.body()
            liveData.postValue(
                if (response.isSuccessful && serverResponse != null) {
                    checkResponse(serverResponse)
                } else {
                    AppState.Error(Throwable(SERVER_ERROR))
                }
            )
        }

        override fun onFailure(call: Call<POMServerResponseData>, e: Throwable?) {
            liveData.postValue(AppState.Error(Throwable(e?.message ?: REQUEST_ERROR)))
        }

        private fun checkResponse(serverResponse: POMServerResponseData): AppState {
            return AppState.SuccessPhoto(serverResponse)
        }
    }
}