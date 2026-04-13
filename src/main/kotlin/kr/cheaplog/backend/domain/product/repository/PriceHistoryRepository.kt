package kr.cheaplog.backend.domain.product.repository

import kr.cheaplog.backend.domain.product.entity.PriceHistory
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import java.time.LocalDateTime

interface PriceHistoryRepository : JpaRepository<PriceHistory, Long> {

    fun findByProductIdAndRecordedAtAfterOrderByRecordedAtDesc(
        productId: Long,
        recordedAt: LocalDateTime
    ): List<PriceHistory>

    fun findByProductIdAndSourceAndRecordedAtAfterOrderByRecordedAtDesc(
        productId: Long,
        source: String,
        recordedAt: LocalDateTime
    ): List<PriceHistory>

    @Modifying
    @Query("DELETE FROM PriceHistory p WHERE p.recordedAt < :before")
    fun deleteByRecordedAtBefore(before: LocalDateTime): Int
}
