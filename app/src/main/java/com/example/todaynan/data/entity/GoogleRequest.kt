package com.example.todaynan.data.entity

import com.google.gson.annotations.SerializedName

data class GoogleRequest(
    @SerializedName(value="grant_type") private val grant_type: String,
    @SerializedName(value="client_id") private val client_id: String,
    @SerializedName(value="client_secret") private val client_secret: String,
    @SerializedName(value="code") private val code: String,
)
