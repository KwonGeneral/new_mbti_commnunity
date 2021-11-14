package com.kwon.new_mbti_community.NonMember

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.AppCompatCheckBox
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.jakewharton.rxbinding4.widget.checkedChanges
import com.kwon.new_mbti_community.Api.ApiService
import com.kwon.new_mbti_community.ChainActivity
import com.kwon.new_mbti_community.Common.*
import com.kwon.new_mbti_community.Contain.ApiDefine.Companion.FCM_TOKEN
import com.kwon.new_mbti_community.Contain.ApiStatus
import com.kwon.new_mbti_community.Contain.ColorStatus
import com.kwon.new_mbti_community.Contain.Define.Companion.FALSE
import com.kwon.new_mbti_community.Contain.Define.Companion.LOGIN_FAIL_STATUS
import com.kwon.new_mbti_community.Contain.Define.Companion.SIGNUP_SUCCESS
import com.kwon.new_mbti_community.Contain.Define.Companion.TRUE
import com.kwon.new_mbti_community.Contain.RegDefine.Companion.USERNAME_REG
import com.kwon.new_mbti_community.Contain.SharedDefine.Companion.ACCESS_TOKEN
import com.kwon.new_mbti_community.Contain.SharedDefine.Companion.AUTO_LOGIN
import com.kwon.new_mbti_community.Contain.SharedDefine.Companion.SHARED_DEFAULT
import com.kwon.new_mbti_community.MbtiApplication
import com.kwon.new_mbti_community.Model.CheckService
import com.kwon.new_mbti_community.R
import com.kwon.new_mbti_community.Api.RxService
import com.kwon.new_mbti_community.ViewModel.LoginSignupViewModel
import com.kwon.new_mbti_community.ViewModel.ScreenViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.HttpException

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        Log.d("TEST", "로그인 액티비티~!")

        // ADS
        MbtiApplication().getADS(this, login_adv)

        /*
        // 회원가입 성공
        if (ScreenViewModel.getInstance().screen_status.value == SIGNUP_SUCCESS) {
            MbtiApplication().snackBarShow(
                login_all_layout,
                SIGNUP_SUCCESS,
                2000,
                ColorStatus.WHITE.color,
                ColorStatus.TEXT_SUCCESS.color
            )
        }*/

        // 프로그레스바 설정
        login_progress_layout.bringToFront()


        // 전체 레이아웃 클릭 시, 포커스 해제
        MbtiApplication().layoutClickKeyboardDown(
            this,
            null,
            null,
            login_all_layout,
            login_username_input,
            login_password_input
        )

        // 텍스트 변환 감지 : 아이디
        login_username_input.addTextChangedListener(textWatcher)
        // 텍스트 변환 감지 : 비밀번호
        login_password_input.addTextChangedListener(textWatcher)

        /*
        ApiService.getInstance(this).login_status.observe(this, { ob ->
            when (ob) {
                1 -> {
                    // 자동 로그인 여부 저장
                    when (login_auto_check_btn.isChecked) {
                        true -> {
                            SharedDB(this@LoginActivity).setString(AUTO_LOGIN, TRUE)
                        }
                        else -> {
                            SharedDB(this@LoginActivity).setString(AUTO_LOGIN, FALSE)
                        }
                    }
                    startActivity(Intent(this@LoginActivity, ChainActivity::class.java))
                    finish()
                }
                2 -> {
                    login_status_message.text = LOGIN_FAIL_STATUS
                    MbtiApplication().viewColorChange(
                        ColorStatus.TEXT_FAIL.color,
                        login_username_circle,
                        login_password_circle
                    )
                }
            }
        })*/

        LoginSignupViewModel(this)?.let { login ->
            login.autoLogin.observe(this, {
                login_auto_check_btn.isChecked = it
            })
            login.status.observe(this, {
                when (it) {
                    ApiStatus.SUCCESS -> {
                        startActivity(Intent(this@LoginActivity, ChainActivity::class.java))
                    }
                    else -> {
//                        arrayOf<AppCompatCheckBox>(login_username_circle, login_password_circle).run {
//                            for (v in this)
//                                v.isChecked = false
//                        }


                        Toast.makeText(applicationContext, it.comment, Toast.LENGTH_SHORT).show()
                    }
                }

            })
            // 로그인 버튼 클릭 이벤트
            login_btn.setOnClickListener {
                // 로그인 상태 메세지
                login.login(
                    login_username_input.text.toString(),
                    login_password_input.text.toString()
                )
            }

            // 회원가입 버튼 클릭 이벤트
            login_signup_btn.setOnClickListener {
                login.signUp()
            }
        }

    }


    // 텍스트 입력창 변화 시, 이벤트
    private val textWatcher: TextWatcher = object : TextWatcher {
        override fun onTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
//            // 아이디
//            MbtiApplication().editCheckViewColor(
//                USERNAME_REG,
//                login_username_input,
//                login_username_circle
//            )
//            // 패스워드
//            MbtiApplication().editCheckViewColor(
//                USERNAME_REG,
//                login_password_input,
//                login_password_circle
//            )
        }

        override fun afterTextChanged(s: Editable?) {}
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
    }
}

private fun AppCompatCheckBox.fail(checked: Boolean) {
    isChecked = checked
}
