package com.example.todaynan.data.entity

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class PostWrite(
    @SerializedName(value="content")val content: String,
    @SerializedName(value="title")val title: String,
    @SerializedName(value="category")val category: String
): Serializable
