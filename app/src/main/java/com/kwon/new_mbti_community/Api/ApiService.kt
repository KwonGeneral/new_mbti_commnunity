package com.kwon.new_mbti_community.Api

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.kwon.new_mbti_community.ChainActivity
import com.kwon.new_mbti_community.Common.Hash
import com.kwon.new_mbti_community.Common.PasswordCheck
import com.kwon.new_mbti_community.Common.SharedDB
import com.kwon.new_mbti_community.Contain.*
import com.kwon.new_mbti_community.MbtiApplication
import com.kwon.new_mbti_community.Model.CheckService
import com.kwon.new_mbti_community.Model.CreateService
import com.kwon.new_mbti_community.Model.SignupData
import com.kwon.new_mbti_community.NonMember.LoginActivity
import com.kwon.new_mbti_community.ViewModel.LoginSignupViewModel
import com.kwon.new_mbti_community.ViewModel.ScreenViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_signup.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response

class ApiService(context: Context) {
    val con = context
    val login_status = MutableLiveData<Int>()

    companion object {
        @SuppressLint("StaticFieldLeak")
        var instance: ApiService? = null

        @JvmName("fragment_getInstance")
        fun getInstance(context: Context): ApiService {
            instance?.let {
                return it
            }
            instance = ApiService(context)
            return instance!!
        }
    }

    // subscribe을 쓰지 않으면 제대로 동작하지 않음. 이 부분 체크해서 찾아보자.
    // subscribeOn은 쓰레드 지정
    // observeOn도 쓰레드 지정으로 보이는데, 뭔지 찾아보자.
    fun loginApi(username: String, password: String, fcm_token: String) =
        RxService.create(con, CheckService::class.java).login(
            Hash().login_hash(
                username,
                PasswordCheck().password_aes256(password),
                fcm_token
            ))
            .subscribeOn(Schedulers.io())
            .map { t ->
                if (t.isSuccessful) {
                    return@map t.body()!!
                } else {
                    throw HttpException(t)
                }
            }
            .observeOn(AndroidSchedulers.mainThread())

//    fun signup_api(username: String, password: String, user_type: String, message: String) {
//        RxService.create(con, CreateService::class.java)?.let { api ->
//            api.createUser(Hash().signup_hash(
//                signup_username_input.text.toString(),
//                PasswordCheck().password_aes256(signup_password_input.text.toString()),
//                signup_nickname_input.text.toString(),
//                LoginSignupViewModel().user_mbti.value.toString(),
//                Define.SIGNUP_MESSAGE
//            )).enqueue(object: Callback<SignupData> {
//                override fun onResponse(call: Call<SignupData>, response: Response<SignupData>) {
//                    signup_progress_layout.visibility = View.GONE
//                    response.body()?.let { body ->
//                        when(body.code) {
//                            ApiStatus.ERROR_OVERLAP_NICKNAME.code -> MbtiApplication().snackBarShow(signup_all_layout,
//                                Define.SIGNUP_OVERLAP_NICKNAME, 2000, ColorStatus.WHITE.color, ColorStatus.BABLACK.color, signup_nickname_circle, ColorStatus.FFBLACK.color)
//                            ApiStatus.ERROR_OVERLAP_USERNAME.code -> MbtiApplication().snackBarShow(signup_all_layout,
//                                Define.SIGNUP_OVERLAP_USERNAME, 2000, ColorStatus.WHITE.color, ColorStatus.BABLACK.color, signup_username_circle, ColorStatus.FFBLACK.color)
//                            ApiStatus.SUCCESS_CREATE.code -> {
//                                ScreenViewModel.getInstance().screen_status.value =
//                                    Define.SIGNUP_SUCCESS
//                                startActivity(Intent(this@SignupActivity, LoginActivity::class.java))
//                                finish()
//                            }
//                        }
//                    }
//                }
//
//                override fun onFailure(call: Call<SignupData>, t: Throwable) {
//                    signup_progress_layout.visibility = View.GONE
//                }
//            })
//        }
//    }
}