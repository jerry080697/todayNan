package com.example.todaynan.data.entity

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class PlaceLikeRequest(
    @SerializedName("title") val title: String,
    @SerializedName("description") val description: String,
    @SerializedName("placeAddress") val placeAddress: String,
    @SerializedName("image") val image: String,
    @SerializedName("category") val category: String
) : Serializable