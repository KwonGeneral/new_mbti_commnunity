package com.kwon.new_mbti_community.Model

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface DeleteService {
    // 게시글 삭제
    @POST("/board/delete/")
    fun deleteBoard(
        @Body parameters: HashMap<String, Int>
    ): Call<DeleteBoardData>

    // 코멘트 삭제
    @POST("/board/comment/delete/")
    fun deleteComment(
        @Body parameters: HashMap<String, Int>
    ): Call<DeleteCommentData>

}