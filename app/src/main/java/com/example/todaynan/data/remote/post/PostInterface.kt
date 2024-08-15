package com.example.todaynan.data.remote.post

import com.example.todaynan.data.entity.PostWrite
import com.example.todaynan.data.entity.ReplyWrite
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface PostInterface {
    // 게시글 작성
    @POST("/post")
    fun post(
        @Header("authorization") accessToken: String,
        @Body post: PostWrite
    ): Call<PostResponse<Post>>

    // 구인 게시판 전체 검색
    @GET("/post/employ")
    fun getPostEmploy(
        @Header("authorization") accessToken: String,
        @Query("page") page: Int
    ): Call<PostResponse<GetPost>>

    // 잡담 게시판 전체 검색
    @GET("/post/chat")
    fun getChatEmploy(
        @Header("authorization") accessToken: String,
        @Query("page") page: Int
    ): Call<PostResponse<GetPost>>

    // 게시글 삭제
    @PATCH("/post/{post_id}")
    fun deletePost(
        @Header("authorization") accessToken: String,
        @Path("post_id") postId: Int
    ): Call<PostResponse<DeletePost>>

    // 댓글 작성
    @POST("/post/comment/{post_id}")
    fun reply(
        @Header("authorization") accessToken: String,
        @Path("post_id") postId: Int,
        @Body comment: ReplyWrite
    ): Call<PostResponse<Reply>>

    // 게시글 세부사항 조회
    @GET("/post/detail/{post_id}")
    fun getReply(
        @Header("authorization") accessToken: String,
        @Path("post_id") postId: Int
    ): Call<PostResponse<GetReply>>
}