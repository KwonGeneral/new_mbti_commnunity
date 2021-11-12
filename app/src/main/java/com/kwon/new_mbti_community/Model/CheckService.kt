package com.kwon.new_mbti_community.Model

import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Observable
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface CheckService {
    // 로그인
    @POST("/account/user/login/")
    fun login(
        @Body parameters: HashMap<String, String>
    ): Maybe<Response<UserData>>
//    ): Call<UserData>
}