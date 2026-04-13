package kr.cheaplog.backend.domain.keyword.dto

data class KeywordCreateRequest(
    val keyword: String
)

data class KeywordUpdateRequest(
    val isActive: Boolean
)
