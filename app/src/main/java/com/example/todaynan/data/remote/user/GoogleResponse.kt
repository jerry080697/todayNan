package com.example.todaynan.data.remote.user

import com.google.gson.annotations.SerializedName

data class GoogleResponse(
    @SerializedName(value="access_token") var access_token: String,
)
