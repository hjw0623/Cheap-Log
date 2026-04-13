package kr.cheaplog.backend.domain.crawllog.entity

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(
    name = "crawl_logs",
    indexes = [
        Index(name = "idx_source_started", columnList = "source, started_at DESC"),
        Index(name = "idx_status", columnList = "status")
    ]
)
class CrawlLog(
    @Column(nullable = false, length = 20)
    val source: String,

    @Column(nullable = false, length = 10)
    val status: String,

    @Column(name = "collected_count")
    val collectedCount: Int = 0,

    @Column(name = "new_count")
    val newCount: Int = 0,

    @Column(name = "parse_fail_count")
    val parseFailCount: Int = 0,

    @Column(name = "error_message", columnDefinition = "TEXT")
    val errorMessage: String? = null,

    @Column(name = "started_at", nullable = false)
    val startedAt: LocalDateTime = LocalDateTime.now(),

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L
)
