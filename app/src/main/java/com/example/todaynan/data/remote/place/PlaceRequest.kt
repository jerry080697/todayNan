package com.example.todaynan.data.remote.place

import com.google.gson.annotations.SerializedName

data class PlaceRequest (
    @SerializedName(value="title")val title: String,
    @SerializedName(value="description")val description: String,
    @SerializedName(value="placeAddress")val placeAddress: String,
    @SerializedName(value="image")val image: String,
    @SerializedName(value="category")var category: String,
)