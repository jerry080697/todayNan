package com.example.todaynan.data.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.example.todaynan.data.remote.user.UserInterface


const val BASE_URL = "http://todayNan.store"

fun getRetrofit(): Retrofit {
    return Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}
