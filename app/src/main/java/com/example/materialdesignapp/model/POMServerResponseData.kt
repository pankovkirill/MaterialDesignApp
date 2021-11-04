package com.example.materialdesignapp.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class POMServerResponseData(
    val photos: List<Preview>
) : Parcelable {
    @Parcelize
    data class Preview(
        val img_src: String,
        val earth_date: String
    ):Parcelable
}