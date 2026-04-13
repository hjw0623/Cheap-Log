package kr.cheaplog.backend.domain.hotdeal.controller

import kr.cheaplog.backend.domain.hotdeal.dto.HotdealDetailResponse
import kr.cheaplog.backend.domain.hotdeal.dto.HotdealListItem
import kr.cheaplog.backend.domain.hotdeal.service.HotdealService
import kr.cheaplog.backend.global.dto.ApiResponse
import kr.cheaplog.backend.global.dto.CursorResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/hotdeals")
class HotdealController(
    private val hotdealService: HotdealService
) {

    @GetMapping
    fun getList(
        @RequestParam(required = false) cursor: Long?,
        @RequestParam(defaultValue = "20") size: Int,
        @RequestParam(required = false) source: String?,
        @RequestParam(required = false) category: String?,
        @RequestParam(defaultValue = "false") isExpired: Boolean,
        @RequestParam(defaultValue = "latest") sort: String
    ): ResponseEntity<ApiResponse<CursorResponse<HotdealListItem>>> {
        val validSize = size.coerceIn(1, 50)
        val response = hotdealService.getList(cursor, validSize, source, category, isExpired, sort)
        return ResponseEntity.ok(ApiResponse.ok(response))
    }

    @GetMapping("/{id}")
    fun getDetail(@PathVariable id: Long): ResponseEntity<ApiResponse<HotdealDetailResponse>> {
        val response = hotdealService.getDetail(id)
        return ResponseEntity.ok(ApiResponse.ok(response))
    }

    @GetMapping("/search")
    fun search(
        @RequestParam q: String,
        @RequestParam(required = false) cursor: Long?,
        @RequestParam(defaultValue = "20") size: Int
    ): ResponseEntity<ApiResponse<CursorResponse<HotdealListItem>>> {
        val validSize = size.coerceIn(1, 50)
        val response = hotdealService.search(q, cursor, validSize)
        return ResponseEntity.ok(ApiResponse.ok(response))
    }
}
