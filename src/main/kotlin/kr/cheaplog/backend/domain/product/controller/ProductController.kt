package kr.cheaplog.backend.domain.product.controller

import kr.cheaplog.backend.domain.product.dto.PriceHistoryResponse
import kr.cheaplog.backend.domain.product.service.ProductService
import kr.cheaplog.backend.global.dto.ApiResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/products")
class ProductController(
    private val productService: ProductService
) {

    @GetMapping("/{id}/price-history")
    fun getPriceHistory(
        @PathVariable id: Long,
        @RequestParam(defaultValue = "30") days: Int,
        @RequestParam(required = false) source: String?
    ): ResponseEntity<ApiResponse<PriceHistoryResponse>> {
        val response = productService.getPriceHistory(id, days, source)
        return ResponseEntity.ok(ApiResponse.ok(response))
    }
}
