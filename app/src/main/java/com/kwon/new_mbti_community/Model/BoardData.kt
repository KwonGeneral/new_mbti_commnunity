package com.kwon.new_mbti_community.Model

data class BoardData(
    val code: String,
    val data: List<BoardDetailList>,
    val message: String
)

data class BoardDetailList(
    val board_content: String,
    val board_like_count: Int,
    val board_nickname: String,
    val board_profile: String,
    val board_title: String,
    val board_type: String,
    val board_user_id: Int,
    val board_user_type: String,
    val board_username: String,
    val created_at: String,
    val id: Int,
    val updated_at: String
)

data class DeleteBoardData(
    val code: String,
    val data: DeleteBoardDetailList,
    val message: String
)

data class DeleteBoardDetailList(
    val board_title: String,
    val id: Int
)