package kr.cheaplog.backend.domain.auth.service

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import kr.cheaplog.backend.domain.auth.dto.LoginRequest
import kr.cheaplog.backend.domain.auth.dto.LoginResponse
import kr.cheaplog.backend.domain.user.entity.User
import kr.cheaplog.backend.domain.user.repository.UserRepository
import kr.cheaplog.backend.global.error.BusinessException
import kr.cheaplog.backend.global.error.ErrorCode
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class AuthService(
    private val firebaseAuth: FirebaseAuth,
    private val userRepository: UserRepository
) {

    @Transactional
    fun login(request: LoginRequest): LoginResponse {
        val decodedToken = try {
            firebaseAuth.verifyIdToken(request.firebaseToken)
        } catch (e: FirebaseAuthException) {
            throw BusinessException(ErrorCode.INVALID_TOKEN)
        }

        val uid = decodedToken.uid
        val email = decodedToken.email ?: throw BusinessException(ErrorCode.INVALID_TOKEN)

        val existingUser = userRepository.findByFirebaseUid(uid)

        return if (existingUser.isPresent) {
            val user = existingUser.get()
            request.fcmToken?.let { user.updateFcmToken(it) }
            LoginResponse(
                userId = user.id,
                email = user.email,
                nickname = user.nickname,
                isNew = false
            )
        } else {
            val nickname = decodedToken.name ?: email.substringBefore("@")
            val user = userRepository.save(
                User(
                    firebaseUid = uid,
                    email = email,
                    nickname = nickname,
                    fcmToken = request.fcmToken
                )
            )
            LoginResponse(
                userId = user.id,
                email = user.email,
                nickname = user.nickname,
                isNew = true
            )
        }
    }

    @Transactional
    fun logout(firebaseUid: String) {
        val user = userRepository.findByFirebaseUid(firebaseUid)
            .orElseThrow { BusinessException(ErrorCode.USER_NOT_FOUND) }
        user.clearFcmToken()
    }
}
