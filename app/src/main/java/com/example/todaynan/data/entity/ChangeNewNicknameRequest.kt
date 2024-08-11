package com.example.todaynan.data.entity

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ChangeNewNicknameRequest(
    @SerializedName(value="nickName")var nickname: String
): Serializable
