package com.kwon.new_mbti_community.NonMember

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.kwon.new_mbti_community.Common.Hash
import com.kwon.new_mbti_community.Common.PasswordCheck
import com.kwon.new_mbti_community.Contain.ApiStatus
import com.kwon.new_mbti_community.Contain.ColorStatus
import com.kwon.new_mbti_community.Contain.Define.Companion.SIGNUP_INPUT_ERROR_MESSAGE
import com.kwon.new_mbti_community.Contain.Define.Companion.SIGNUP_MESSAGE
import com.kwon.new_mbti_community.Contain.Define.Companion.SIGNUP_OVERLAP_NICKNAME
import com.kwon.new_mbti_community.Contain.Define.Companion.SIGNUP_OVERLAP_USERNAME
import com.kwon.new_mbti_community.Contain.Define.Companion.SIGNUP_SELECT_SPINNER
import com.kwon.new_mbti_community.Contain.Define.Companion.SIGNUP_SUCCESS
import com.kwon.new_mbti_community.Contain.Define.Companion.TEXT_BLANK
import com.kwon.new_mbti_community.Contain.RegDefine.Companion.NICKNAME_REG
import com.kwon.new_mbti_community.Contain.RegDefine.Companion.PASSWORD_REG
import com.kwon.new_mbti_community.Contain.RegDefine.Companion.USERNAME_REG
import com.kwon.new_mbti_community.MbtiApplication
import com.kwon.new_mbti_community.Model.CreateService
import com.kwon.new_mbti_community.Model.SignupData
import com.kwon.new_mbti_community.R
import com.kwon.new_mbti_community.ViewModel.LoginSignupViewModel
import com.kwon.new_mbti_community.ViewModel.ScreenViewModel
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_signup.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignupActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

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

            // 값 체크
            if(LoginSignupViewModel(this).input_signup_login_check_count.value == 0) {
                MbtiApplication().snackBarShow(signup_all_layout, SIGNUP_INPUT_ERROR_MESSAGE, 2000, ColorStatus.WHITE.color, ColorStatus.TEXT_FAIL.color)
                return@setOnClickListener
            } else {
                // 입력 값 공백 체크
                if(MbtiApplication().textIsBlank(signup_username_input.text.toString(), signup_password_input.text.toString(), signup_nickname_input.text.toString())) {
                    MbtiApplication().snackBarShow(signup_all_layout, SIGNUP_INPUT_ERROR_MESSAGE, 2000, ColorStatus.WHITE.color, ColorStatus.TEXT_FAIL.color)
                    return@setOnClickListener
                }
            }

            signup_progress_layout.visibility = View.VISIBLE

//            Connect.create(this, CreateService::class.java)?.let { api ->
//                api.createUser(Hash().signup_hash(
//                    signup_username_input.text.toString(),
//                    PasswordCheck().password_aes256(signup_password_input.text.toString()),
//                    signup_nickname_input.text.toString(),
//                    LoginSignupViewModel().user_mbti.value.toString(),
//                    SIGNUP_MESSAGE
//                )).enqueue(object: Callback<SignupData> {
//                    override fun onResponse(call: Call<SignupData>, response: Response<SignupData>) {
//                        signup_progress_layout.visibility = View.GONE
//                        response.body()?.let { body ->
//                            when(body.code) {
//                                ApiStatus.ERROR_OVERLAP_NICKNAME.code -> MbtiApplication().snackBarShow(signup_all_layout, SIGNUP_OVERLAP_NICKNAME, 2000, ColorStatus.WHITE.color, ColorStatus.BABLACK.color, signup_nickname_circle, ColorStatus.FFBLACK.color)
//                                ApiStatus.ERROR_OVERLAP_USERNAME.code -> MbtiApplication().snackBarShow(signup_all_layout, SIGNUP_OVERLAP_USERNAME, 2000, ColorStatus.WHITE.color, ColorStatus.BABLACK.color, signup_username_circle, ColorStatus.FFBLACK.color)
//                                ApiStatus.SUCCESS_CREATE.code -> {
//                                    ScreenViewModel.getInstance().screen_status.value = SIGNUP_SUCCESS
//                                    startActivity(Intent(this@SignupActivity, LoginActivity::class.java))
//                                    finish()
//                                }
//                            }
//                        }
//                    }
//
//                    override fun onFailure(call: Call<SignupData>, t: Throwable) {
//                        signup_progress_layout.visibility = View.GONE
//                    }
//                })
//            }
        }
    }

    override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
        if(parent.getItemAtPosition(position).toString() == SIGNUP_SELECT_SPINNER) {
            LoginSignupViewModel(this).input_signup_login_check_count.value = 0
        } else {
            LoginSignupViewModel(this).input_signup_login_check_count.value = 1
        }
    }
    override fun onNothingSelected(parent: AdapterView<*>?) {}

    // 텍스트 입력창 변화 시, 이벤트
    private val textWatcher: TextWatcher = object : TextWatcher {
        override fun onTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            MbtiApplication().editCheckViewColor(USERNAME_REG, signup_username_input, signup_username_circle) // 아이디
            MbtiApplication().editCheckViewColor(PASSWORD_REG, signup_password_input, signup_password_circle) // 패스워드
            MbtiApplication().editCheckViewColor(NICKNAME_REG, signup_nickname_input, signup_nickname_circle) // 닉네임
            if(signup_password_input.text.toString() == signup_password_check_input.text.toString() && signup_password_check_input.text.toString() != TEXT_BLANK) {
                MbtiApplication().editCheckViewColor(PASSWORD_REG, signup_password_check_input, signup_password_check_circle) // 패스워드 확인
                LoginSignupViewModel(this@SignupActivity).input_signup_login_check_count.value = 1
            } else if(signup_password_input.text.toString() != signup_password_check_input.text.toString() && signup_password_check_input.text.toString() != TEXT_BLANK) {
                MbtiApplication().viewColorChange(ColorStatus.TEXT_DEFAULT.color, signup_password_circle, signup_password_check_circle)
                LoginSignupViewModel(this@SignupActivity).input_signup_login_check_count.value = 0
            }
        }
        override fun afterTextChanged(s: Editable?) { }
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }
    }
}