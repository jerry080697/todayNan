package com.example.todaynan.data.remote.place

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
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
}
