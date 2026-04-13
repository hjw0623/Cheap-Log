package kr.cheaplog.backend.domain.user.dto

data class UserUpdateRequest(
    val nickname: String? = null,
    val fcmToken: String? = null
)
