package com.example.todaynan.data.remote.place

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface PlaceInterface {
    //안 놀거리 검색
    @GET("/place/search/inside")
    fun searchInside(
        @Header("authorization") accessToken: String,
    ): Call<PlaceResponse<Inside>>

    // 밖 놀거리 검색
    @GET("/place/search/outside")
    fun searchOutside(
        @Header("authorization") accessToken: String,
        @Query("searchString") searchString: String,
        @Query("pageToken") pageToken: String?,
    ): Call<PlaceResponse<Outside>>

    // 좋아요 모아보기
    @GET("/place/like")
    fun listLike(
        @Header("authorization") accessToken: String,
    ): Call<PlaceResponse<LikeALL>>

    // 좋아요 등록
    @POST("/place/like")
    fun addLike(
        @Header("authorization") accessToken: String,
        @Body request: PlaceRequest,
    ): Call<PlaceResponse<AddResult>>

    // 좋아요 삭제
    @DELETE("/place/like/{like_id}")
    fun deleteLike(
        @Header("authorization") accessToken: String,
        @Path("like_id") likeId: Int
    ): Call<PlaceResponse<String>>
}
