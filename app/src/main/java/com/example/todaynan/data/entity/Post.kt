package com.example.todaynan.data.entity

data class Post(
    var userName: String,
    var profileImg: Int,
    var creationTime: String,
    var location: String,
    var postTitle: String,
    var postContent: String,
    var boardTitle: String,
    var likedNum: Int,
    var replyNum: Int,
    var category: String
)
