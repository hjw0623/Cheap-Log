package kr.cheaplog.backend.global.scheduler

import kr.cheaplog.backend.domain.crawllog.repository.CrawlLogRepository
import kr.cheaplog.backend.domain.product.repository.PriceHistoryRepository
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

/**
 * 데이터 보관 정책에 따라 오래된 데이터를 정리한다.
 * - price_history: 90일 초과 삭제
 * - crawl_logs: 30일 초과 삭제
 *
 * 매일 새벽 3시에 실행.
 */
@Component
class DataCleanupScheduler(
    private val priceHistoryRepository: PriceHistoryRepository,
    private val crawlLogRepository: CrawlLogRepository
) {

    private val log = LoggerFactory.getLogger(javaClass)

    @Scheduled(cron = "0 0 3 * * *")
    @Transactional
    fun cleanup() {
        val now = LocalDateTime.now()

        val priceHistoryDeleted = priceHistoryRepository.deleteByRecordedAtBefore(
            now.minusDays(90)
        )
        log.info("price_history 정리 완료: ${priceHistoryDeleted}건 삭제 (90일 초과)")

        val crawlLogDeleted = crawlLogRepository.deleteByStartedAtBefore(
            now.minusDays(30)
        )
        log.info("crawl_logs 정리 완료: ${crawlLogDeleted}건 삭제 (30일 초과)")
    }
}
