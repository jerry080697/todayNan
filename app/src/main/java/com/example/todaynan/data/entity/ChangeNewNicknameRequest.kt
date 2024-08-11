package com.example.todaynan.data.entity

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ChangeNewNicknameRequest(
    @SerializedName(value="request")var nickname: String
): Serializable
