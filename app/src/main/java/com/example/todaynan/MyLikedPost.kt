package com.example.todaynan

data class MyLikedPost(
    val userName: String, val profileImg: Int, val creationTime: String,
    val location:String, val postTitle:String, val boardTitle:String, val likedNum:Int,
    val replyNum:Int)
