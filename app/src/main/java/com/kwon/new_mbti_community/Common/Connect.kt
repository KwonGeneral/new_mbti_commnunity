package com.kwon.new_mbti_community.Common

import android.content.Context
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Connect {
    companion object {
        fun <T> create(context: Context, service: Class<T>): T {
            return connect(context).create(service) as T
        }
        private fun connect(context: Context): Retrofit {
            return Retrofit.Builder()
                .addConverterFactory(
                    GsonConverterFactory.create(
                        GsonBuilder().setLenient().create()
                    )
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