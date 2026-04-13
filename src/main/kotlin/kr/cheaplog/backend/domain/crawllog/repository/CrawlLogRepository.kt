package kr.cheaplog.backend.domain.crawllog.repository

import kr.cheaplog.backend.domain.crawllog.entity.CrawlLog
import org.springframework.data.jpa.repository.JpaRepository

interface CrawlLogRepository : JpaRepository<CrawlLog, Long>
