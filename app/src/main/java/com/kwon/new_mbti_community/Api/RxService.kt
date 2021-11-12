package com.kwon.new_mbti_community.Api

import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.util.Log
import android.view.View
import com.google.gson.GsonBuilder
import com.kwon.new_mbti_community.ChainActivity
import com.kwon.new_mbti_community.Common.Hash
import com.kwon.new_mbti_community.Common.PasswordCheck
import com.kwon.new_mbti_community.Common.SharedDB
import com.kwon.new_mbti_community.Contain.*
import com.kwon.new_mbti_community.Model.CheckService
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import okhttp3.OkHttpClient
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface RxService {

    companion object {
        fun <T> create(context: Context, service: Class<T>): T {
            return connect(context).create(service) as T
        }
        private fun connect(context: Context): Retrofit {
            return Retrofit.Builder()
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .addConverterFactory(
                    GsonConverterFactory.create(GsonBuilder().setLenient().create())
                )
                .baseUrl("https://kwonputer.com")
                .client(OkHttpClient.Builder().addInterceptor { chain ->
                    SharedDB.getInstance(context)?.getToken()!!.let {
                        chain.proceed(chain.request().newBuilder().addHeader("PrivatKey", "Kwonputer")
                            .addHeader("AccessToken", it).build())
                    }
                }.build())
                .build()
        }
    }
}