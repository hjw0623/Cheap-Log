package kr.cheaplog.backend.global.dto

data class CursorResponse<T>(
    val items: List<T>,
    val nextCursor: Long?,
    val hasNext: Boolean
)
