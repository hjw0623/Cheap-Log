package kr.cheaplog.backend.domain.crawllog.controller

import kr.cheaplog.backend.domain.crawllog.dto.CrawlLogRequest
import kr.cheaplog.backend.domain.crawllog.dto.CrawlLogResponse
import kr.cheaplog.backend.domain.crawllog.service.CrawlLogService
import kr.cheaplog.backend.global.dto.ApiResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/internal/crawl-logs")
class InternalCrawlLogController(
    private val crawlLogService: CrawlLogService
) {

    @PostMapping
    fun create(@RequestBody request: CrawlLogRequest): ResponseEntity<ApiResponse<CrawlLogResponse>> {
        val response = crawlLogService.save(request)
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.ok(response))
    }
}
