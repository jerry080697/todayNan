package com.example.todaynan

data class Recommend(
    var category: String = "",
    var image: Int? = null,
    var title: String = "",
    var info: String = "",
    var detail: String = "",
    var isLike: Boolean = false
)
