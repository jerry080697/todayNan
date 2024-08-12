package com.example.todaynan.data.entity

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ChangeLocationRequest(
    @SerializedName(value="request")var address: String
): Serializable
