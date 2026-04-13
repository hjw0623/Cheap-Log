package kr.cheaplog.backend.domain.user.controller

import kr.cheaplog.backend.domain.user.dto.UserResponse
import kr.cheaplog.backend.domain.user.dto.UserUpdateRequest
import kr.cheaplog.backend.domain.user.dto.UserUpdateResponse
import kr.cheaplog.backend.domain.user.service.UserService
import kr.cheaplog.backend.global.dto.ApiResponse
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/users")
class UserController(
    private val userService: UserService
) {

    @GetMapping("/me")
    fun getMe(request: HttpServletRequest): ResponseEntity<ApiResponse<UserResponse>> {
        val firebaseUid = request.getAttribute("firebaseUid") as String
        val response = userService.getMe(firebaseUid)
        return ResponseEntity.ok(ApiResponse.ok(response))
    }

    @PutMapping("/me")
    fun updateMe(
        request: HttpServletRequest,
        @RequestBody body: UserUpdateRequest
    ): ResponseEntity<ApiResponse<UserUpdateResponse>> {
        val firebaseUid = request.getAttribute("firebaseUid") as String
        val response = userService.updateMe(firebaseUid, body)
        return ResponseEntity.ok(ApiResponse.ok(response))
    }

    @DeleteMapping("/me")
    fun deleteMe(request: HttpServletRequest): ResponseEntity<ApiResponse<Nothing>> {
        val firebaseUid = request.getAttribute("firebaseUid") as String
        userService.deleteMe(firebaseUid)
        return ResponseEntity.ok(ApiResponse.ok())
    }
}
