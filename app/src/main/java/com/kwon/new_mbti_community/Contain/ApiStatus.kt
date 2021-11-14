package com.kwon.new_mbti_community.Contain

import com.kwon.new_mbti_community.Contain.Define.Companion.SIGNUP_SUCCESS

enum class ApiStatus(val code:String,val comment:String) {
    SUCCESS_CREATE("S0001",SIGNUP_SUCCESS),
    SUCCESS_UPDATE("S0002", "수정 완료"),
    SUCCESS_DELETE("S0003","삭제 완료"),
    SUCCESS("S0004","성공"),
    SUCCESS_LOGIN("S0008","로그인 성공"),

    ERROR_KEY("E0002","키 오류"),
    ERROR_OVERLAP_NICKNAME("E0003","닉네임 중복"),
    ERROR_OVERLAP_USERNAME("E0004","아이디 중복"),
    FAIL_LOGIN("E0005","로그인 실패")

}