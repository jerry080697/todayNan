package com.example.todaynan.data.remote.user

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class UserResponse<T> (
    @SerializedName(value="isSuccess")val isSuccess: Boolean,
    @SerializedName(value="code")val code: String,
    @SerializedName(value="message")val message: String,
    @SerializedName(value="result")val result: T,
): Serializable

data class Token(
    @SerializedName(value="user_id")val userId: Int,
    @SerializedName(value="created_at")val createdAt: String,
    @SerializedName(value="accessToken")val accessToken: String,
    @SerializedName(value="refreshToken")val refreshToken: String,
): Serializable

data class Login(
    @SerializedName(value="user_id")val userId: Int,
    @SerializedName(value="accessToken")val accessToken: String,
    @SerializedName(value="refreshToken")val refreshToken: String,
    @SerializedName(value="expiration")val expiration: String,
): Serializable

data class ChangeNickNameResponse(
    @SerializedName(value = "message")val message: String
):Serializable

