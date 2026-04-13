package kr.cheaplog.backend.domain.auth.controller

import kr.cheaplog.backend.domain.auth.dto.LoginRequest
import kr.cheaplog.backend.domain.auth.dto.LoginResponse
import kr.cheaplog.backend.domain.auth.service.AuthService
import kr.cheaplog.backend.global.dto.ApiResponse
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/auth")
class AuthController(
    private val authService: AuthService
) {

    @PostMapping("/login")
    fun login(@RequestBody request: LoginRequest): ResponseEntity<ApiResponse<LoginResponse>> {
        val response = authService.login(request)
        return ResponseEntity.ok(ApiResponse.ok(response))
    }

    @PostMapping("/logout")
    fun logout(request: HttpServletRequest): ResponseEntity<ApiResponse<Nothing>> {
        val firebaseUid = request.getAttribute("firebaseUid") as String
        authService.logout(firebaseUid)
        return ResponseEntity.ok(ApiResponse.ok())
    }
}
