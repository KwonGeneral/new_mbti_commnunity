package com.kwon.new_mbti_community.NonMember

import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.FrameLayout
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.snackbar.Snackbar
import com.kwon.new_mbti_community.Common.Connect
import com.kwon.new_mbti_community.Common.Hash
import com.kwon.new_mbti_community.Contain.ApiStatus
import com.kwon.new_mbti_community.Contain.ColorStatus
import com.kwon.new_mbti_community.Contain.Define.Companion.SIGNUP_MESSAGE
import com.kwon.new_mbti_community.Contain.Define.Companion.SIGNUP_OVERLAP_NICKNAME
import com.kwon.new_mbti_community.Contain.Define.Companion.SIGNUP_OVERLAP_USERNAME
import com.kwon.new_mbti_community.Contain.RegDefine.Companion.NICKNAME_REG
import com.kwon.new_mbti_community.Contain.RegDefine.Companion.PASSWORD_REG
import com.kwon.new_mbti_community.Contain.RegDefine.Companion.USERNAME_REG
import com.kwon.new_mbti_community.MbtiApplication
import com.kwon.new_mbti_community.Model.CheckService
import com.kwon.new_mbti_community.Model.CreateService
import com.kwon.new_mbti_community.Model.SignupData
import com.kwon.new_mbti_community.R
import com.kwon.new_mbti_community.ViewModel.LoginSignupViewModel
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_signup.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignupActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        LoginSignupViewModel.getInstance().input_username_check_count.observe(this, { ob ->
            Log.d("TEST", "추적~! : $ob")
        })

        // ADS
        MbtiApplication().getADS(this, signup_adv)

        // 프로그레스바 설정
        signup_progress_layout.bringToFront()

        // Input 길이 제한
        MbtiApplication().setMaxLength(signup_nickname_input, 20, 8)
        MbtiApplication().setMaxLength(signup_username_input, 20, 8)
        MbtiApplication().setMaxLength(signup_password_input, 20, 8)
        MbtiApplication().setMaxLength(signup_password_check_input, 20, 8)
        signup_progress_layout.bringToFront()

        // 스피너
        ArrayAdapter.createFromResource(this, R.array.mbti_select_array, android.R.layout.simple_spinner_item).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            mbti_spinner.adapter = adapter
            mbti_spinner.onItemSelectedListener = this
        }

        // 화면 클릭 및 스피너 클릭 시 키보드 내리고 포커스 클리어
        MbtiApplication().layoutClickKeyboardDown(this, null, null, signup_all_layout,
            signup_username_input, signup_nickname_input, signup_password_input, signup_password_check_input)
        MbtiApplication().spinnerClickKeyboardDown(this, null, null, mbti_spinner,
            signup_username_input, signup_nickname_input, signup_password_input, signup_password_check_input)

        // 입력 값 변화 탐지
        signup_username_input.addTextChangedListener(textWatcher)
        signup_password_input.addTextChangedListener(textWatcher)
        signup_password_check_input.addTextChangedListener(textWatcher)
        signup_nickname_input.addTextChangedListener(textWatcher)

        // 회원가입 버튼 클릭
        signup_submit_btn.setOnClickListener {
            // 키보드 내리기
            val mInputMethodManager = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            mInputMethodManager.hideSoftInputFromWindow(currentFocus?.windowToken, 0)

            signup_progress_layout.visibility = View.VISIBLE

            Connect.create(this, CreateService::class.java)?.let { api ->
                api.createUser(Hash().signup_hash(
                    signup_username_input.text.toString(),
                    signup_password_input.text.toString(),
                    signup_nickname_input.text.toString(),
                    LoginSignupViewModel().user_mbti.value.toString(),
                    SIGNUP_MESSAGE
                )).enqueue(object: Callback<SignupData> {
                    override fun onResponse(call: Call<SignupData>, response: Response<SignupData>) {
                        signup_progress_layout.visibility = View.GONE
                        val body = response.body()

                        if(body != null) {
                            if(body.code == ApiStatus.ERROR_OVERLAP_NICKNAME.code) {
                                // 닉네임 중복 오류
                                snackBarShow(signup_all_layout, SIGNUP_OVERLAP_NICKNAME, 2000, ColorStatus.WHITE.color, ColorStatus.BABLACK.color, signup_nickname_circle, ColorStatus.FFBLACK.color)
                            } else if(body.code == ApiStatus.ERROR_OVERLAP_USERNAME.code) {
                                // 아이디 중복 오류
                                snackBarShow(signup_all_layout, SIGNUP_OVERLAP_USERNAME, 2000, ColorStatus.WHITE.color, ColorStatus.BABLACK.color, signup_username_circle, ColorStatus.FFBLACK.color)
                            } else if(body.code == "S0001") {
                                startActivity(Intent(this@SignupActivity, LoginActivity::class.java))
                            }
                        }
                    }

                    override fun onFailure(call: Call<SignupData>, t: Throwable) {
                        signup_progress_layout.visibility = View.GONE
                    }
                })
            }
        }
//
//        // 회원가입 버튼 클릭
//        signup_submit_btn.setOnClickListener {
//            // 키보드 내리기
//            val mInputMethodManager = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
//            mInputMethodManager.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
//
//            if(username_count == 0) {
//                val snack: Snackbar = Snackbar
//                    .make(findViewById<ConstraintLayout>(R.id.signup_all_layout), "아이디가 올바르지 않습니다", 2000)
//                    .setBackgroundTint(Color.parseColor("#ffffff"))
//                    .setTextColor(Color.parseColor("#ba000000"))
//
//                val snack_view = snack.view
//                val params = snack_view.layoutParams as FrameLayout.LayoutParams
//                params.gravity = Gravity.TOP
//                snack_view.layoutParams = params
//                snack.show()
//
//                signup_username_circle.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#ff0000"))
//
//                return@setOnClickListener
//            }
//            if(password_count == 0) {
//                val snack: Snackbar = Snackbar
//                    .make(findViewById<ConstraintLayout>(R.id.signup_all_layout), "비밀번호를 확인해주세요", 2000)
//                    .setBackgroundTint(Color.parseColor("#ffffff"))
//                    .setTextColor(Color.parseColor("#ba000000"))
//
//                val snack_view = snack.view
//                val params = snack_view.layoutParams as FrameLayout.LayoutParams
//                params.gravity = Gravity.TOP
//                snack_view.layoutParams = params
//                snack.show()
//
//                signup_password_circle.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#ff0000"))
//                signup_password_check_circle.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#ff0000"))
//
//                return@setOnClickListener
//            }
//            if(nickname_count == 0) {
//                val snack: Snackbar = Snackbar
//                    .make(findViewById<ConstraintLayout>(R.id.signup_all_layout), "닉네임을 확인해주세요", 2000)
//                    .setBackgroundTint(Color.parseColor("#ffffff"))
//                    .setTextColor(Color.parseColor("#ba000000"))
//
//                val snack_view = snack.view
//                val params = snack_view.layoutParams as FrameLayout.LayoutParams
//                params.gravity = Gravity.TOP
//                snack_view.layoutParams = params
//                snack.show()
//
//                signup_nickname_circle.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#ff0000"))
//
//                return@setOnClickListener
//            }
//            if(mbti_count == 0) {
//                val snack: Snackbar = Snackbar
//                    .make(findViewById<ConstraintLayout>(R.id.signup_all_layout), "MBTI를 선택해주세요", 2000)
//                    .setBackgroundTint(Color.parseColor("#ffffff"))
//                    .setTextColor(Color.parseColor("#ba000000"))
//
//                val snack_view = snack.view
//                val params = snack_view.layoutParams as FrameLayout.LayoutParams
//                params.gravity = Gravity.TOP
//                snack_view.layoutParams = params
//                snack.show()
//
//                return@setOnClickListener
//            }
//
//            val password_aes = PasswordCheck().password_aes256(temp_password)
//
//            // 회원가입 API 통신
//            val parameter:HashMap<String, String> = HashMap()
//            if(password_aes != null) {
//                parameter["password"] = password_aes
//            } else {
//                return@setOnClickListener
//            }
//
//            signup_progress_layout.visibility = View.VISIBLE
//
//            parameter["username"] = temp_username
//            parameter["nickname"] = temp_nickname
//            parameter["user_type"] = temp_select_mbti
//            parameter["message"] = "상태 메세지를 입력해주세요."
//
//            signup_api.createUser(parameter).enqueue(object: Callback<SignupData> {
//                override fun onResponse(call: Call<SignupData>, response: Response<SignupData>) {
//                    signup_progress_layout.visibility = View.GONE
//                    val body = response.body()
//
//                    if(body != null) {
//                        if(body.code == "E0003") {
//                            // 닉네임 중복 오류
//                            val snack: Snackbar = Snackbar
//                                .make(findViewById<ConstraintLayout>(R.id.signup_all_layout), "중복된 닉네임이 존재합니다.", 2000)
//                                .setBackgroundTint(Color.parseColor("#ffffff"))
//                                .setTextColor(Color.parseColor("#ba000000"))
//
//                            val snack_view = snack.view
//                            val params = snack_view.layoutParams as FrameLayout.LayoutParams
//                            params.gravity = Gravity.TOP
//                            snack_view.layoutParams = params
//                            snack.show()
//
//                            signup_nickname_circle.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#ff0000"))
//
//                        } else if(body.code == "E0004") {
//                            // 아이디 중복 오류
//                            val snack: Snackbar = Snackbar
//                                .make(findViewById<ConstraintLayout>(R.id.signup_all_layout), "중복된 아이디가 존재합니다.", 2000)
//                                .setBackgroundTint(Color.parseColor("#ffffff"))
//                                .setTextColor(Color.parseColor("#ba000000"))
//
//                            val snack_view = snack.view
//                            val params = snack_view.layoutParams as FrameLayout.LayoutParams
//                            params.gravity = Gravity.TOP
//                            snack_view.layoutParams = params
//                            snack.show()
//
//                            signup_username_circle.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#ff0000"))
//                        } else if(body.code == "S0001") {
//                            MoveActivity().login_move(this@SignupActivity, "signup_status", "success")
//                        }
//                    }
//
////                    Log.d("TEST", "Signup - createUser 통신성공 바디 -> $body")
//                }
//
//                override fun onFailure(call: Call<SignupData>, t: Throwable) {
//                    signup_progress_layout.visibility = View.GONE
////                    Log.d("TEST", "Signup - createUser 통신실패 에러 -> " + t.message)
//                }
//            })
//        }
    }

    override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
        if(parent.getItemAtPosition(position).toString() == "MBTI를 선택해주세요") {
            LoginSignupViewModel.getInstance().input_username_check_count.value = 0
        } else {
            LoginSignupViewModel.getInstance().input_username_check_count.value = 1
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {}

    // 텍스트 입력창 변화 시, 이벤트
    private val textWatcher: TextWatcher = object : TextWatcher {
        override fun onTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            // 아이디
            MbtiApplication().editCheckViewColor(USERNAME_REG, signup_username_input, signup_username_circle)
            // 패스워드
            MbtiApplication().editCheckViewColor(PASSWORD_REG, signup_password_input, signup_password_circle)
            // 패스워드 확인
            MbtiApplication().editCheckViewColor(PASSWORD_REG, signup_password_check_input, signup_password_check_circle)
            // 닉네임
            MbtiApplication().editCheckViewColor(NICKNAME_REG, signup_nickname_input, signup_nickname_circle)
        }
        override fun afterTextChanged(s: Editable?) { }
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }
    }

    private fun snackBarShow(layout: ConstraintLayout, text: String, duration: Int, back_color: String, text_color: String, view: View, view_color: String) {
        val snack: Snackbar = Snackbar
            .make(layout, text, duration)
            .setBackgroundTint(Color.parseColor(back_color))
            .setTextColor(Color.parseColor(text_color))

        val snack_view = snack.view
        val params = snack_view.layoutParams as FrameLayout.LayoutParams
        params.gravity = Gravity.TOP
        snack_view.layoutParams = params
        snack.show()

        view.backgroundTintList = ColorStateList.valueOf(Color.parseColor(view_color))
    }
}