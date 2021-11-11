package com.kwon.new_mbti_community.Model

import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

interface ReadService {
    // 전체 게시판 조회
//    @GET("/board/")
//    fun getBoard(
//        @Query("board_type") board_type: String,
//        @Query("page") page: String
//    ): Call<BoardData>

    // 게시판 조회
    @GET("/board/")
    fun getBoardUserType(
        @Query("board_type") board_type: String,
        @Query("board_user_type") board_user_type: String = "",
        @Query("page") page: String
    ): Call<BoardData>

    // 코멘트 조회
    @GET("/board/comment/")
    fun getComment(
        @Query("board_id") board_id: Int,
        @Query("page") page: String
    ): Call<CommentData>

    // 유저 정보 가져오기
    @GET("/account/user/")
    fun getUserData(
        @Query("username") username: String,
    ): Call<UserData>

    // 유저 게시글 카운트
    @GET("/board/user_board_count/")
    fun getBoardCount(
        @Query("username") username: String,
    ): Call<BoardCountData>

    // 유저가 올린 게시글만 가져오기
    @GET("/board/")
    fun getUserBoard(
        @Query("board_username") board_username: String,
        @Query("page") page: String
    ): Call<BoardData>










}