package com.kwon.new_mbti_community.Common

import com.kwon.new_mbti_community.decByKey
import com.kwon.new_mbti_community.encByKey

class PasswordCheck {
    fun check(regex: String, str: String): MatchResult? {
        val reg = Regex(regex)
        return reg.matchEntire(str)
    }

    fun password_aes256(password:String): String {
        var encText: String? = null
        try {
            val key = "kwonputer7777777"
            encText = encByKey(key, password).toString()
            val decText: String? = decByKey(key, encText)
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }

        return encText!!
    }

    fun check_aes256(encText:String): String {
        var decText: String? = null
        try {
            val key = "kwonputer7777777"
            decText = decByKey(key, encText)
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }

        return decText!!
    }
}