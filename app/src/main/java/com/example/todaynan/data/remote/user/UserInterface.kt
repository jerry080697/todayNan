package com.example.todaynan.data.remote.user

import com.example.todaynan.data.entity.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface UserInterface {

    // 회원가입
    @POST("/user/signup")
    fun signUp(
        @Header("accessToken") authorization: String,
        @Query("loginType") type: String,
        @Body user: User
    ): Call<UserResponse<Token>>

}