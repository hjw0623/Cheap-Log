package kr.cheaplog.backend.global.auth

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class FirebaseAuthFilter(
    private val firebaseAuth: FirebaseAuth
) : OncePerRequestFilter() {

    override fun shouldNotFilter(request: HttpServletRequest): Boolean {
        val path = request.requestURI
        return PERMIT_ALL_PATHS.any { path.startsWith(it) }
    }

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val authHeader = request.getHeader("Authorization")

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            sendError(response, HttpServletResponse.SC_UNAUTHORIZED, "INVALID_TOKEN", "인증 토큰이 필요합니다")
            return
        }

        val token = authHeader.substring(7)

        try {
            val decodedToken = firebaseAuth.verifyIdToken(token)
            request.setAttribute("firebaseUid", decodedToken.uid)
            request.setAttribute("firebaseEmail", decodedToken.email)
            filterChain.doFilter(request, response)
        } catch (e: FirebaseAuthException) {
            val (code, message) = if (e.errorCode.name == "EXPIRED_ID_TOKEN") {
                "TOKEN_EXPIRED" to "토큰이 만료되었습니다"
            } else {
                "INVALID_TOKEN" to "Firebase 토큰 검증 실패"
            }
            sendError(response, HttpServletResponse.SC_UNAUTHORIZED, code, message)
        }
    }

    private fun sendError(response: HttpServletResponse, status: Int, code: String, message: String) {
        response.status = status
        response.contentType = "application/json;charset=UTF-8"
        response.writer.write("""{"success":false,"data":null,"message":"$message","code":"$code"}""")
    }

    companion object {
        private val PERMIT_ALL_PATHS = listOf(
            "/api/auth/login",
            "/api/hotdeals",
            "/api/products",
            "/api/internal/"
        )
    }
}
