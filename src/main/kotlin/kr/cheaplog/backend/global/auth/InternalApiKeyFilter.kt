package kr.cheaplog.backend.global.auth

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class InternalApiKeyFilter(
    @Value("\${internal.api-key}")
    private val internalApiKey: String
) : OncePerRequestFilter() {

    override fun shouldNotFilter(request: HttpServletRequest): Boolean {
        return !request.requestURI.startsWith("/api/internal/")
    }

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val apiKey = request.getHeader("X-Internal-Key")

        if (apiKey == null || apiKey != internalApiKey) {
            response.status = HttpServletResponse.SC_UNAUTHORIZED
            response.contentType = "application/json;charset=UTF-8"
            response.writer.write(
                """{"success":false,"data":null,"message":"내부 API Key가 올바르지 않습니다","code":"INTERNAL_KEY_INVALID"}"""
            )
            return
        }

        filterChain.doFilter(request, response)
    }
}
