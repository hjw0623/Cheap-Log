package kr.cheaplog.backend.domain.auth.dto

data class LoginRequest(
    val firebaseToken: String,
    val fcmToken: String? = null
)
