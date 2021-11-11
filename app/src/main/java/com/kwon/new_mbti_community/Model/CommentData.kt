package com.kwon.new_mbti_community.Model

data class CommentData(
    val code: String,
    val data: List<CommentDetailList>,
    val message: String
)

data class CommentDetailList(
    val board_id: Int,
    val comment_content: String,
    val comment_like_count: Int,
    val comment_nickname: String,
    val comment_profile: String,
    val comment_title: String,
    val comment_user_id: Int,
    val comment_user_type: String,
    val comment_username: String,
    val created_at: String,
    val id: Int,
    val updated_at: String
)

data class DeleteCommentData(
    val code: String,
    val data: DeleteCommentDetailList,
    val message: String
)

data class DeleteCommentDetailList(
    val comment_title: String,
    val id: Int
)