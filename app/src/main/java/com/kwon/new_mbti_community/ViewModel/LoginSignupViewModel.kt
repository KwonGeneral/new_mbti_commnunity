package com.kwon.new_mbti_community.ViewModel

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import com.kwon.new_mbti_community.Contain.Define.Companion.LOGIN_FAIL_STATUS
import com.kwon.new_mbti_community.Contain.Define.Companion.LOGIN_SUCCESS_STATUS

class LoginSignupViewModel {
    var input_username_check_count = MutableLiveData<Int>(0)
    var input_password_check_count = MutableLiveData<Int>(0)
    var user_mbti = MutableLiveData<String>("ISTJ")

    companion object {
        @SuppressLint("StaticFieldLeak")
        var instance: LoginSignupViewModel? = null

        @JvmName("fragment_getInstance")
        fun getInstance(): LoginSignupViewModel {
            instance?.let {
                return it
            }
            instance = LoginSignupViewModel()
            return instance!!
        }
    }

    fun login_input_check(): String {
        return if(input_password_check_count.value == 1 && input_username_check_count.value == 1) {
            LOGIN_SUCCESS_STATUS
        } else {
            LOGIN_FAIL_STATUS
        }
    }
}