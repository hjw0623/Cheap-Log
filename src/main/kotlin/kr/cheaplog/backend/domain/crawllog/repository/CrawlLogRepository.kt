package kr.cheaplog.backend.domain.crawllog.repository

import kr.cheaplog.backend.domain.crawllog.entity.CrawlLog
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import java.time.LocalDateTime

interface CrawlLogRepository : JpaRepository<CrawlLog, Long> {

    @Modifying
    @Query("DELETE FROM CrawlLog c WHERE c.startedAt < :before")
    fun deleteByStartedAtBefore(before: LocalDateTime): Int
}
