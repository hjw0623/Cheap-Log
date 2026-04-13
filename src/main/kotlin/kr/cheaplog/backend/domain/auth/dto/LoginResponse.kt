package kr.cheaplog.backend.domain.auth.dto

data class LoginResponse(
    val userId: Long,
    val email: String,
    val nickname: String,
    val isNew: Boolean
)
