package kr.cheaplog.backend.domain.keyword.controller

import kr.cheaplog.backend.domain.keyword.dto.*
import kr.cheaplog.backend.domain.keyword.service.KeywordService
import kr.cheaplog.backend.global.dto.ApiResponse
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/keywords")
class KeywordController(
    private val keywordService: KeywordService
) {

    @GetMapping
    fun getKeywords(request: HttpServletRequest): ResponseEntity<ApiResponse<KeywordListResponse>> {
        val firebaseUid = request.getAttribute("firebaseUid") as String
        val response = keywordService.getKeywords(firebaseUid)
        return ResponseEntity.ok(ApiResponse.ok(response))
    }

    @PostMapping
    fun createKeyword(
        request: HttpServletRequest,
        @RequestBody body: KeywordCreateRequest
    ): ResponseEntity<ApiResponse<KeywordResponse>> {
        val firebaseUid = request.getAttribute("firebaseUid") as String
        val response = keywordService.createKeyword(firebaseUid, body)
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.ok(response))
    }

    @PutMapping("/{id}")
    fun updateKeyword(
        request: HttpServletRequest,
        @PathVariable id: Long,
        @RequestBody body: KeywordUpdateRequest
    ): ResponseEntity<ApiResponse<KeywordResponse>> {
        val firebaseUid = request.getAttribute("firebaseUid") as String
        val response = keywordService.updateKeyword(firebaseUid, id, body)
        return ResponseEntity.ok(ApiResponse.ok(response))
    }

    @DeleteMapping("/{id}")
    fun deleteKeyword(
        request: HttpServletRequest,
        @PathVariable id: Long
    ): ResponseEntity<ApiResponse<Nothing>> {
        val firebaseUid = request.getAttribute("firebaseUid") as String
        keywordService.deleteKeyword(firebaseUid, id)
        return ResponseEntity.ok(ApiResponse.ok())
    }
}
