package com.example.todaynan.base

import android.app.Application

class AppData : Application() {

    companion object{
        var socialToken: String = ""
        var socialType: String = ""
        var address: String = ""
        var preferStr: ArrayList<String> = ArrayList()
        var preferIdx: List<Long> = ArrayList()
        var nickname: String = ""
        var mypet: String = ""
    }

}