package kr.cheaplog.backend.domain.product.dto

import java.time.LocalDateTime

data class PriceHistoryResponse(
    val productId: Long,
    val productName: String,
    val histories: List<PriceHistoryItem>
)

data class PriceHistoryItem(
    val source: String,
    val price: Int,
    val recordedAt: LocalDateTime
)
