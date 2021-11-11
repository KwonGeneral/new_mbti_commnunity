package com.kwon.new_mbti_community.Model

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface CheckService {
    // 로그인
    @POST("/account/user/login/")
    fun login(
        @Body parameters: HashMap<String, String>
    ): Call<UserData>
}