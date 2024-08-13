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

data class PostResponse(
    @SerializedName(value="post_id")val post_id: Int,
    @SerializedName(value="user_id")val user_id: Int,
    @SerializedName(value="title")val title: String,
    @SerializedName(value="content")val content: String,
    @SerializedName(value="category")val category: String
): Serializable

data class ChangeNickNameResponse(
    @SerializedName(value = "message")val message: String
):Serializable

data class PostList(
    @SerializedName(value = "postId") val postId: Int,
    @SerializedName(value = "userId") val userId: Int,
    @SerializedName(value = "userNickname") val userNickname: String,
    @SerializedName(value = "userAddress") val userAddress: String,
    @SerializedName(value = "postTitle") val postTitle: String,
    @SerializedName(value = "postContent") val postContent: String,
    @SerializedName(value = "postLike") val postLike: Int,
    @SerializedName(value = "postComment") val postComment: Int,
    @SerializedName(value = "createdAt") val createdAt: String
): Serializable

data class GetPost(
    @SerializedName(value = "postList") val postList: List<PostList>,
    @SerializedName(value = "listSize") val listSize: Int,
    @SerializedName(value = "totalPage") val totalPage: Int,
    @SerializedName(value = "totalElements") val totalElements: Int,
    @SerializedName(value = "isFirst") val isFirst: Boolean,
    @SerializedName(value = "isLast") val isLast: Boolean
) : Serializable
