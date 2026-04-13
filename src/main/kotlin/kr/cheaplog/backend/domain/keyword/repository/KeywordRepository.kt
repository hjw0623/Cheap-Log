package kr.cheaplog.backend.domain.keyword.repository

import kr.cheaplog.backend.domain.keyword.entity.Keyword
import org.springframework.data.jpa.repository.JpaRepository

interface KeywordRepository : JpaRepository<Keyword, Long> {
    fun findByUserIdOrderByCreatedAtDesc(userId: Long): List<Keyword>
    fun countByUserId(userId: Long): Int
    fun existsByUserIdAndKeyword(userId: Long, keyword: String): Boolean
    fun findByIsActiveTrue(): List<Keyword>
}
