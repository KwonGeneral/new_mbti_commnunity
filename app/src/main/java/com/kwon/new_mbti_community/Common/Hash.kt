package com.kwon.new_mbti_community.Common

import android.text.Editable
import android.text.TextWatcher

class Hash {
    fun login_hash(username: String, password: String, fcm_token: String): HashMap<String, String> {
        return HashMap(mapOf("username" to username, "password" to password, "fcm_token" to fcm_token))
    }
    fun signup_hash(username: String, password: String, nickname: String, user_type: String, message: String): HashMap<String, String> {
        return HashMap(mapOf("username" to username, "password" to password, "nickname" to nickname, "user_type" to user_type, "message" to message))
    }
}