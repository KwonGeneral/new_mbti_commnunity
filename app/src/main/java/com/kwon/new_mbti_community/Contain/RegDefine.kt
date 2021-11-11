package com.kwon.new_mbti_community.Contain

class RegDefine {
    companion object {
        const val USERNAME_REG:String = "^[0-9a-z].{1,15}\$"
        const val PASSWORD_REG:String = "^([a-zA-Z!@#\$%^&*0-9]).{1,15}\$"
        const val NICKNAME_REG:String = "^([a-zA-Z0-9ㄱ-ㅎ|ㅏ-ㅣ|가-힣]).{1,12}\$"
    }
}