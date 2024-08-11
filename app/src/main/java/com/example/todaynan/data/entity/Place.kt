package com.example.todaynan.data.entity

data class Place(
    var placeName:String,
    var placeAddress:String,
    var placeDescription:String,
    var latitude: Double?,
    var longitude: Double?,
    var isLike:Boolean,
    var imgUrl:String
)