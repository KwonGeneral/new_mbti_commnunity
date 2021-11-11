package com.kwon.new_mbti_community.Model

data class SignupData(
    val code: String,
    val data: SignupDetailList,
    val message: String
)

data class SignupDetailList(
    val created_at: String,
    val id: Int,
    val is_active: Boolean,
    val message: Any,
    val nickname: String,
    val password: String,
    val profile: String,
    val updated_at: String,
    val user_type: Any,
    val username: String
)

data class UserData(
    val code: String,
    val data: TokenInfoList,
    val message: String
)

data class TokenInfoList(
    val access_token: String,
    val user_info: UserDetailList
)

data class UserDetailList(
    val created_at: String,
    val id: Int,
    val is_active: Boolean,
    val message: String,
    val fcm_token: String,
    val push_setting: String,
    val nickname: String,
    val password: String,
    val profile: String,
    val updated_at: String,
    val user_type: String,
    val username: String
)

data class BoardCountData(
    val code: String,
    val data: BoardCount,
    val message: String
)

data class BoardCount(
    val board_total_count: String
)

data class UpdateUserProfileData(
    val code: String,
    val data: String,
    val message: String
)

data class UpdateUserInfoData(
    val code: String,
    val data: UpdateUserDetailList,
    val message: String
)

data class UpdateUserDetailList(
    val nickname: String,
    val user_type: String,
    val message: String,
    val password: String
)