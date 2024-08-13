package com.example.todaynan.data.entity

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class SearchOutsideRequest(
    @SerializedName(value="searchString")var searchString: String
): Serializable
