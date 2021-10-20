package com.example.materialdesignapp.model

import retrofit2.Callback

class RepositoryImpl(
    private val remoteDataSource: RemoteDataSource
) : Repository {
    override fun getData(date: String, callback: Callback<PODServerResponseData>) {
        remoteDataSource.getDataFromServer(date ,callback)
    }
}