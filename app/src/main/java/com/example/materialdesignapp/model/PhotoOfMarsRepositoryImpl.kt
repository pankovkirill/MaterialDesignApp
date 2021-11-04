package com.example.materialdesignapp.model

import retrofit2.Callback

class PhotoOfMarsRepositoryImpl(
    private val remoteDataSource: RemoteDataSource
) : PhotoOfMarsRepository {
    override fun getData(callback: Callback<POMServerResponseData>) {
        remoteDataSource.getDataFromServerPOM(callback)
    }
}