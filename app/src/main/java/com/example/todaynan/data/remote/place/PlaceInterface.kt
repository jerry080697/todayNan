package com.example.todaynan.data.remote.place

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface PlaceInterface {
    //안 놀거리 검색
    @GET("/place/search/inside")
    fun searchInside(
        @Header("authorization") accessToken: String,
    ): Call<PlaceResponse<Inside>>
}
