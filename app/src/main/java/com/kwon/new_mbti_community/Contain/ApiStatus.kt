package com.kwon.new_mbti_community.Contain

enum class ApiStatus(val code:String) {
    SUCCESS_CREATE("S0001"),
    SUCCESS_UPDATE("S0002"),
    SUCCESS_DELETE("S0003"),
    SUCCESS("S0004"),

    ERROR_KEY("E0002"),
    ERROR_OVERLAP_NICKNAME("E0003"),
    ERROR_OVERLAP_USERNAME("E0004"),
    FAIL_LOGIN("E0005"),
}