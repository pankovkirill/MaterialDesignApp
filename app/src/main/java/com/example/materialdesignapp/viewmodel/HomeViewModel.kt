package com.example.materialdesignapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.materialdesignapp.model.PODServerResponseData
import com.example.materialdesignapp.model.RemoteDataSource
import com.example.materialdesignapp.model.RepositoryImpl
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import kotlin.jvm.Throws

private const val SERVER_ERROR = "Ошибка сервера"
private const val REQUEST_ERROR = "Ошибка запроса на сервер"

class HomeViewModel(
    val liveData: MutableLiveData<AppState> = MutableLiveData(),
    private val repository: RepositoryImpl = RepositoryImpl(RemoteDataSource())
) : ViewModel() {

    fun getData(date: String) {
        liveData.value = AppState.Loading
        repository.getData(date, callback)
    }

    private val callback = object : Callback<PODServerResponseData> {
        @Throws(IOException::class)
        override fun onResponse(
            call: Call<PODServerResponseData>,
            response: Response<PODServerResponseData>
        ) {
            val serverResponse: PODServerResponseData? = response.body()
            liveData.postValue(
                if (response.isSuccessful && serverResponse != null) {
                    checkResponse(serverResponse)
                } else {
                    AppState.Error(Throwable(SERVER_ERROR))
                }
            )
        }

        override fun onFailure(call: Call<PODServerResponseData>, e: Throwable?) {
            liveData.postValue(AppState.Error(Throwable(e?.message ?: REQUEST_ERROR)))
        }

        private fun checkResponse(serverResponse: PODServerResponseData): AppState {
            return AppState.Success(serverResponse)
        }
    }
}