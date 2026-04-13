package kr.cheaplog.backend.domain.hotdeal.dto

data class HotdealBatchResponse(
    val totalCount: Int,
    val newCount: Int,
    val updatedCount: Int,
    val results: List<HotdealBatchResult>
)

data class HotdealBatchResult(
    val sourceId: String,
    val id: Long,
    val isNew: Boolean
)
