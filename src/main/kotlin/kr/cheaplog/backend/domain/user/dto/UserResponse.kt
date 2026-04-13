package kr.cheaplog.backend.domain.user.dto

import java.time.LocalDateTime

data class UserResponse(
    val id: Long,
    val email: String,
    val nickname: String,
    val keywordCount: Int,
    val createdAt: LocalDateTime
)
