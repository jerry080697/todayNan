package com.example.todaynan.data.entity

data class Post(
    val userName: String,
    val profileImg: Int,
    val creationTime: String,
    val location:String,
    val postTitle:String,
    val postContent:String,
    val boardTitle:String,
    val likedNum:Int,
    val replyNum:Int
)
