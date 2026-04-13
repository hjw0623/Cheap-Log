package kr.cheaplog.backend.domain.hotdeal.controller

import kr.cheaplog.backend.domain.hotdeal.dto.HotdealBatchRequest
import kr.cheaplog.backend.domain.hotdeal.dto.HotdealBatchResponse
import kr.cheaplog.backend.domain.hotdeal.service.InternalHotdealService
import kr.cheaplog.backend.global.dto.ApiResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/internal/hotdeals")
class InternalHotdealController(
    private val internalHotdealService: InternalHotdealService
) {

    @PostMapping("/batch")
    fun batch(@RequestBody request: HotdealBatchRequest): ResponseEntity<ApiResponse<HotdealBatchResponse>> {
        val response = internalHotdealService.processBatch(request)
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.ok(response))
    }
}
