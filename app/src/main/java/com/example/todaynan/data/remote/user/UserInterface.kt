package com.example.todaynan.data.remote.user

import com.example.todaynan.data.entity.ChangeNewNicknameRequest
import com.example.todaynan.data.entity.GoogleRequest
import com.example.todaynan.data.entity.PostWrite
import com.example.todaynan.data.entity.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Query
interface UserInterface {
    // 구글 accessToken 발급
    @POST("oauth2/v4/token")
    fun getAccessToken(
        @Body request: GoogleRequest
    ): Call<GoogleResponse>

    // 회원가입
    @POST("/user/signup")
    fun signUp(
        @Header("accessToken") authorization: String,
        @Query("loginType") type: String,
        @Body user: User
    ): Call<UserResponse<Token>>
    // 로그인
    @GET("/user/login/")
    fun login(
        @Query("accessToken") accessToken: String,
        @Query("loginType") type: String,
    ): Call<UserResponse<Login>>

    // 자동 로그인
    @GET("/user/auto-login/")
    fun autoLogin(
        @Header("authorization") accessToken: String,
    ): Call<UserResponse<Login>>

    // 게시글 작성
    @POST("/post")
    fun post(
        @Header("authorization") accessToken: String,
        @Body post: PostWrite
    ): Call<UserResponse<PostResponse>>

    //닉네임 변경
    @PATCH("/user/nickname")
    fun changeNickname(
        @Header("authorization") accessToken: String,
        @Body request: ChangeNewNicknameRequest
    ): Call<UserResponse<ChangeNickNameResponse>>

    // 구인 게시판 전체 검색
    @GET("/post/employ")
    fun getPostEmploy(
        @Header("authorization") accessToken: String,
        @Query("page") page: Int
    ): Call<UserResponse<GetPost>>
}