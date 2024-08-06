package com.example.todaynan.data.entity

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class User(
    @SerializedName(value="nickName")var nickname: String,
    @SerializedName(value="preferCategory")var prefer: List<Long>,
    @SerializedName(value="address")var address: String,
    @SerializedName(value="mypet")var mypet: String,
): Serializable
