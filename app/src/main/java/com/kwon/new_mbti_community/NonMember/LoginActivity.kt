package com.kwon.new_mbti_community.NonMember

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.kwon.new_mbti_community.ChainActivity
import com.kwon.new_mbti_community.Common.Connect
import com.kwon.new_mbti_community.Common.Hash
import com.kwon.new_mbti_community.Common.PasswordCheck
import com.kwon.new_mbti_community.Common.SharedDB
import com.kwon.new_mbti_community.Contain.ApiDefine.Companion.FCM_TOKEN
import com.kwon.new_mbti_community.Contain.ApiStatus
import com.kwon.new_mbti_community.Contain.ColorStatus
import com.kwon.new_mbti_community.Contain.Define.Companion.FALSE
import com.kwon.new_mbti_community.Contain.Define.Companion.LOGIN_FAIL_STATUS
import com.kwon.new_mbti_community.Contain.Define.Companion.TRUE
import com.kwon.new_mbti_community.Contain.RegDefine.Companion.USERNAME_REG
import com.kwon.new_mbti_community.Contain.SharedDefine.Companion.ACCESS_TOKEN
import com.kwon.new_mbti_community.Contain.SharedDefine.Companion.AUTO_LOGIN
import com.kwon.new_mbti_community.Contain.SharedDefine.Companion.SHARED_DEFAULT
import com.kwon.new_mbti_community.MbtiApplication
import com.kwon.new_mbti_community.Model.CheckService
import com.kwon.new_mbti_community.Model.UserData
import com.kwon.new_mbti_community.R
import com.kwon.new_mbti_community.ViewModel.LoginSignupViewModel
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // ADS
        MbtiApplication().getADS(this, login_adv)
//        MobileAds.initialize(this)
//        login_adv.loadAd(AdRequest.Builder().build())

        // FCM
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            SharedDB(this).setString(FCM_TOKEN, task.result.toString())
            if (!task.isSuccessful) {
                return@OnCompleteListener
            }
        })

        // 프로그레스바 설정
        login_progress_layout.bringToFront()

        // Input 길이 제한
        MbtiApplication().setMaxLength(login_username_input, 20, 8)
        MbtiApplication().setMaxLength(login_password_input, 20, 8)

        // 전체 레이아웃 클릭 시, 포커스 해제
        MbtiApplication().layoutClickKeyboardDown(this, null, null, login_all_layout, login_username_input, login_password_input)

        // 텍스트 변환 감지 : 아이디
        login_username_input.addTextChangedListener(textWatcher)
        // 텍스트 변환 감지 : 비밀번호
        login_password_input.addTextChangedListener(textWatcher)

        // 로그인 버튼 클릭 이벤트
        login_btn.setOnClickListener {
            // 로그인 상태 메세지
            login_status_message.text = LoginSignupViewModel.getInstance().login_input_check()

            // 로그인 API 통신
            Connect.create(this, CheckService::class.java)?.let { api ->
                api.login(Hash().login_hash(
                    login_username_input.text.toString(),
                    PasswordCheck().password_aes256(login_password_input.text.toString()),
                    SharedDB(this).getString(FCM_TOKEN, SHARED_DEFAULT))).enqueue(object: Callback<UserData> {
                    override fun onResponse(call: Call<UserData>, response: Response<UserData>) {
                        login_progress_layout.visibility = View.GONE
                        response.body()?.let {
                            when(it.code) {
                                // 로그인 실패
                                ApiStatus.FAIL_LOGIN.code -> {
                                    login_status_message.text = LOGIN_FAIL_STATUS
                                    login_username_circle.backgroundTintList = ColorStateList.valueOf(Color.parseColor(ColorStatus.TEXT_FAIL.color))
                                    login_password_circle.backgroundTintList = ColorStateList.valueOf(Color.parseColor(ColorStatus.TEXT_FAIL.color))
                                }
                                else -> {
                                    // 자동 로그인 여부 저장
                                    when(login_auto_check_btn.isChecked) {
                                        true -> {SharedDB(this@LoginActivity).setString(AUTO_LOGIN, TRUE)}
                                        else -> {SharedDB(this@LoginActivity).setString(AUTO_LOGIN, FALSE)}
                                    }
                                    // AccessToken 저장
                                    SharedDB(this@LoginActivity).setString(ACCESS_TOKEN, it.data.access_token)
                                    startActivity(Intent(this@LoginActivity, ChainActivity::class.java))
                                    finish()
                                }
                            }
                        }
                    }
                    override fun onFailure(call: Call<UserData>, t: Throwable) {
                        login_progress_layout.visibility = View.GONE
                    }
                })
                }
            }

        // 회원가입 버튼 클릭 이벤트
        login_signup_btn.setOnClickListener {
            startActivity(Intent(this@LoginActivity, SignupActivity::class.java))
        }
    }

    // 텍스트 입력창 변화 시, 이벤트
    private val textWatcher: TextWatcher = object : TextWatcher {
        override fun onTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            // 아이디
            MbtiApplication().editCheckViewColor(USERNAME_REG, login_username_input, login_username_circle)
            // 패스워드
            MbtiApplication().editCheckViewColor(USERNAME_REG, login_password_input, login_password_circle)
        }
        override fun afterTextChanged(s: Editable?) { }
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }
    }
}