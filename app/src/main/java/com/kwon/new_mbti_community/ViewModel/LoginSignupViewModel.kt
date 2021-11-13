package com.kwon.new_mbti_community.ViewModel

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.kwon.new_mbti_community.Api.ApiService
import com.kwon.new_mbti_community.ChainActivity
import com.kwon.new_mbti_community.Common.SharedDB
import com.kwon.new_mbti_community.Contain.ApiStatus
import com.kwon.new_mbti_community.Contain.Define.Companion.LOGIN_FAIL_STATUS
import com.kwon.new_mbti_community.Contain.Define.Companion.LOGIN_NOW_LOADING
import com.kwon.new_mbti_community.Contain.Define.Companion.LOGIN_SUCCESS_STATUS
import com.kwon.new_mbti_community.Contain.Define.Companion.LOGIN_WAIT
import com.kwon.new_mbti_community.Contain.SharedDefine
import com.kwon.new_mbti_community.NonMember.LoginActivity
import com.kwon.new_mbti_community.NonMember.SignupActivity
import kotlin.reflect.KClass

class LoginSignupViewModel(val context: Context) {
    var input_signup_login_check_count = MutableLiveData<Int>(0)
    var signUpComment = MutableLiveData<String>()
    var moveScreen = MutableLiveData<String>()
    var status = MutableLiveData<ApiStatus>()
    var autoLogin = MutableLiveData<Boolean>()
    var user_mbti = MutableLiveData<String>("ISTJ")


    enum class MENU(val menu: String) {
        SIGN_UP("SIGN_UP"), LOGIN("LOGIN")
    }

    init {
        status.value = ApiStatus.FAIL_LOGIN
        autoLogin.value = SharedDB(context).isAutoLogin()
    }

    fun login(id: String, password: String) {
        signUpComment.value = LOGIN_NOW_LOADING
        ApiService.getInstance(context).loginApi(
            id,
            password,
            SharedDB(context).getFcmToken()
        ).doOnSuccess {
            when (it?.code) {
                ApiStatus.SUCCESS_LOGIN.code -> {
                    status.value = ApiStatus.SUCCESS
                    SharedDB(context).setString(SharedDefine.ACCESS_TOKEN, it?.data.access_token)
                }
                else -> {
                    status.value = ApiStatus.FAIL_LOGIN
                }
            }
        }.doOnError {
            status.value = ApiStatus.FAIL_LOGIN
        }.subscribe()
    }

    fun signUp() {
        moveScreen.value = SignupActivity::class.java.name
    }


    fun login_input_check(): String {
        return if (input_signup_login_check_count.value == 1) {
            LOGIN_SUCCESS_STATUS
        } else {
            LOGIN_FAIL_STATUS
        }
    }
}

