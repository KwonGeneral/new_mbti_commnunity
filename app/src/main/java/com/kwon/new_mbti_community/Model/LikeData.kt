package com.kwon.new_mbti_community.Model

data class LikeBoardData(
    val code: String,
    val data: LikeBoardDetailList,
    val message: String
)

data class LikeBoardDetailList(
    val id: Int,
    val username: String,
    val board: Int,
)

data class LikeCommentData(
    val code: String,
    val data: LikeCommentDetailList,
    val message: String
)

data class LikeCommentDetailList(
    val id: Int,
    val username: String,
    val comment: Int,
)