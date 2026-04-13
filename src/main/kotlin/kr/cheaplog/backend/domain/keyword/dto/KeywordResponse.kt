package kr.cheaplog.backend.domain.keyword.dto

import kr.cheaplog.backend.domain.keyword.entity.Keyword
import java.time.LocalDateTime

data class KeywordResponse(
    val id: Long,
    val keyword: String,
    val isActive: Boolean,
    val createdAt: LocalDateTime
) {
    companion object {
        fun from(entity: Keyword) = KeywordResponse(
            id = entity.id,
            keyword = entity.keyword,
            isActive = entity.isActive,
            createdAt = entity.createdAt
        )
    }
}

data class KeywordListResponse(
    val keywords: List<KeywordResponse>,
    val total: Int
)
