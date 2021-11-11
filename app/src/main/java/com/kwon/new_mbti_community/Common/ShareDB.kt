package com.kwon.new_mbti_community.Common

import android.content.Context
import android.content.SharedPreferences
import java.lang.Exception

class SharedDB(context: Context) {

    companion object {
        var ins:SharedDB? = null
        fun getInstance(context: Context):SharedDB? {
            if (ins == null)
                ins = SharedDB(context)
            return ins
        }
    }


    // 파일 이름과 저장할 Key 값을 만들고, prefs 인스턴스 초기화 ( Context.MODE_PRIVATE == 0 )
    private val prefs: SharedPreferences =
        context.getSharedPreferences("prefs_name", Context.MODE_PRIVATE)

    // prefs 조회
    fun getString(key: String, defValue: String): String {
        return prefs.getString(key, defValue).toString()
    }

    // prefs 저장
    fun setString(key: String, str: String) {
        prefs.edit().putString(key, str).apply()
    }

    fun getToken(): String {
        return prefs.getString("AccessToken", "").toString()
    }

}