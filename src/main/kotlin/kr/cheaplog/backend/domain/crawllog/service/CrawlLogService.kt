package kr.cheaplog.backend.domain.crawllog.service

import kr.cheaplog.backend.domain.crawllog.dto.CrawlLogRequest
import kr.cheaplog.backend.domain.crawllog.dto.CrawlLogResponse
import kr.cheaplog.backend.domain.crawllog.entity.CrawlLog
import kr.cheaplog.backend.domain.crawllog.repository.CrawlLogRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class CrawlLogService(
    private val crawlLogRepository: CrawlLogRepository
) {

    fun save(request: CrawlLogRequest): CrawlLogResponse {
        val crawlLog = crawlLogRepository.save(
            CrawlLog(
                source = request.source,
                status = request.status,
                collectedCount = request.collectedCount ?: 0,
                newCount = request.newCount ?: 0,
                parseFailCount = request.parseFailCount ?: 0,
                errorMessage = request.errorMessage
            )
        )
        return CrawlLogResponse(id = crawlLog.id)
    }
}
