package kr.cheaplog.backend.domain.crawllog.dto

data class CrawlLogRequest(
    val source: String,
    val status: String,
    val collectedCount: Int? = 0,
    val newCount: Int? = 0,
    val parseFailCount: Int? = 0,
    val errorMessage: String? = null
)
