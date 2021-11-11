package com.kwon.new_mbti_community.NonMember

import android.Manifest
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.kwon.new_mbti_community.ChainActivity
import com.kwon.new_mbti_community.Common.SharedDB
import com.kwon.new_mbti_community.Contain.Define.Companion.TRUE
import com.kwon.new_mbti_community.Contain.SharedDefine.Companion.AUTO_LOGIN
import com.kwon.new_mbti_community.Contain.SharedDefine.Companion.SHARED_DEFAULT
import com.kwon.new_mbti_community.R

class StartActivity : AppCompatActivity() {
    // 권한 체크
    val permission_list = arrayOf(
        Manifest.permission.INTERNET,
        Manifest.permission.ACCESS_NETWORK_STATE,
        Manifest.permission.READ_EXTERNAL_STORAGE,
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)
        requestPermissions(permission_list, 0)

        startActivity(Intent(this, LoginActivity::class.java))
//        // 자동 로그인 여부 확인
//        when(SharedDB.getInstance(this)?.getString(AUTO_LOGIN, SHARED_DEFAULT)) {
//            TRUE -> { startActivity(Intent(this, ChainActivity::class.java)) }
//            else -> { startActivity(Intent(this, LoginActivity::class.java)) }
//        }
    }
}