package com.example.todaynan.data.remote.user

import com.example.todaynan.base.AppData
import com.example.todaynan.data.entity.ChangeLocationRequest
import com.example.todaynan.data.entity.ChangeNewNicknameRequest
import com.example.todaynan.data.entity.GoogleRequest
import com.example.todaynan.data.entity.SearchOutsideRequest
import com.example.todaynan.data.entity.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
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

    //닉네임 변경
    @PATCH("/user/nickname")
    fun changeNickname(
        @Header("authorization") accessToken: String,
        @Body request: ChangeNewNicknameRequest
    ): Call<UserResponse<ChangeNickNameResponse>>

    //닉네임 중복 확인
    @GET("/user/signup/{nickname}")
    fun checkNicknameDuplicate(
        @Header("authorization") accessToken: String,
        @Path("nickname") nickname: String
    ): Call<UserResponse<NicknameDuplicateResponse>>

    //내 동네 변경
    @PATCH("/user/address")
    fun changeLocation(
        @Header("authorization") accessToken: String,
        @Body request: ChangeLocationRequest
    ): Call<UserResponse<ChangeLocationResponse>>

    //회원탈퇴
    @DELETE("/user/signout")
    fun signOut(
        @Header("authorization") accessToken: String
    ): Call<UserResponse<SignOutResponse>>

    //장소 검색 밖
    @GET("/place/search/outside")
    fun searchOutside(
        @Header("authorization") accessToken: String,
        @Query("searchString") searchString: String,
        @Query("pageToken") pageToken: String
    ): Call<UserResponse<SearchOutsideResponse>>
}