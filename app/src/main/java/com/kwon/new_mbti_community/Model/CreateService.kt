package com.kwon.new_mbti_community.Model

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface CreateService {
    // 게시글 생성
    @POST("/board/")
    fun createBoard(
        @Body parameters: HashMap<String, String>
    ): Call<BoardData>

    // 게시판 좋아요 클릭
    @POST("/like/board/")
    fun likeBoard(
        @Body parameters: HashMap<String, Int>
    ): Call<LikeBoardData>

    // 코멘트 생성
    @POST("/board/comment/")
    fun createComment(
        @Body parameters: HashMap<String, String>
    ): Call<CommentData>

    // 코멘트 좋아요 클릭
    @POST("/like/comment/")
    fun likeComment(
        @Body parameters: HashMap<String, Int>
    ): Call<LikeCommentData>

    // 회원가입
    @POST("/account/user/")
    fun createUser(
        @Body parameters: HashMap<String, String>
    ): Call<SignupData>
}