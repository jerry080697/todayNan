package com.example.todaynan.data.remote.post

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class PostResponse<T> (
    @SerializedName(value="isSuccess")val isSuccess: Boolean,
    @SerializedName(value="code")val code: String,
    @SerializedName(value="message")val message: String,
    @SerializedName(value="result")val result: T,
): Serializable

data class Post(
    @SerializedName(value="post_id")val post_id: Int,
    @SerializedName(value="user_id")val user_id: Int,
    @SerializedName(value="title")val title: String,
    @SerializedName(value="content")val content: String,
    @SerializedName(value="category")val category: String
): Serializable

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

data class DeletePost(
    @SerializedName(value="isSuccess")val isSuccess: Boolean,
    @SerializedName(value="code")val code: String,
    @SerializedName(value="message")val message: String,
    @SerializedName(value="result")val result: String
) : Serializable

data class Reply(
    @SerializedName(value="post_comment_id")val postCommentId: Int,
    @SerializedName(value="post_id")val postId: Int,
    @SerializedName(value="user_id")val userId: Int,
    @SerializedName(value="comment")val comment: String
) : Serializable

data class GetReply(
    @SerializedName(value = "post_id") val postId: Int,
    @SerializedName(value = "nick_name") val nickName: String,
    @SerializedName(value = "myPet") val myPet: String,
    @SerializedName(value = "title") val title: String,
    @SerializedName(value = "content") val content: String,
    @SerializedName(value = "post_like_cnt") val postLikeCnt: Int,
    @SerializedName(value = "createdAt") val createdAt: String,
    @SerializedName(value = "postCommentList") val postCommentList: List<PostCommentList>
) : Serializable

data class PostCommentList(
    @SerializedName(value = "post_comment_id") val postCommentId: Int,
    @SerializedName(value = "nick_name") val nickName: String,
    @SerializedName(value = "myPet") val myPet: String,
    @SerializedName(value = "content") val content: String,
    @SerializedName(value = "post_comment_like_cnt") val postCommentLikeCnt: Int,
    @SerializedName(value = "createdAt") val createdAt: String
) : Serializable

data class PostLike(
    @SerializedName(value = "post_like_id") val postLikeId: Int,
    @SerializedName(value = "post_id") val postId: Int,
    @SerializedName(value = "user_id") val userId: Int
) : Serializable

data class ReplyLike(
    @SerializedName(value = "post_comment_like_id") val postCommentLikeId: Int,
    @SerializedName(value = "post_comment_id") val postCommentId: Int,
    @SerializedName(value = "user_id") val userId: Int
) : Serializable

data class DeleteReply(
    @SerializedName(value="isSuccess")val isSuccess: Boolean,
    @SerializedName(value="code")val code: String,
    @SerializedName(value="message")val message: String,
    @SerializedName(value="result")val result: String
) : Serializable

data class LikePost(
    @SerializedName(value = "post_like_id") val postLikeId: Int,
    @SerializedName(value = "post_id") val postId: Int,
    @SerializedName(value = "user_id") val userId: Int
) : Serializable

