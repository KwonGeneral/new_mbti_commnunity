package com.kwon.new_mbti_community.Model

import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

interface UpdateService {
    // 게시글 수정
    @PUT("/board/update/{id}")
    fun updateBoard(
        @Path("id") id:String,
        @Body parameters: HashMap<String, String>
    ): Call<BoardData>

    // 코멘트 수정
    @PUT("/board/comment/update/{id}")
    fun updateComment(
        @Path("id") id:String,
        @Body parameters: HashMap<String, String>
    ): Call<CommentData>

    // 회원정보 프로필 수정
    @Multipart
    @PUT("/account/user/update/{username}")
    fun updateUserProfile(
        @Path("username") username:String,
        @Part part: MultipartBody.Part?,
    ): Call<UpdateUserProfileData>

    // 회원정보 데이터 수정
    @PUT("/account/user/update/{username}")
    fun updateUserInfo(
        @Path("username") username:String,
        @Body parameters: HashMap<String, String>
    ): Call<UpdateUserInfoData>
}