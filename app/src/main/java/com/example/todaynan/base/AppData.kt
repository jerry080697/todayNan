package com.example.todaynan.base

import android.app.Application

class AppData : Application() {

    companion object{
        var address: String = ""
        var perfer: ArrayList<String> = ArrayList()
        var nickname: String = ""
        var mypet: String = ""
    }

}