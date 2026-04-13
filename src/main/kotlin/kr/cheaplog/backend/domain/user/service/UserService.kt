package kr.cheaplog.backend.domain.user.service

import kr.cheaplog.backend.domain.keyword.repository.KeywordRepository
import kr.cheaplog.backend.domain.user.dto.UserResponse
import kr.cheaplog.backend.domain.user.dto.UserUpdateRequest
import kr.cheaplog.backend.domain.user.dto.UserUpdateResponse
import kr.cheaplog.backend.domain.user.repository.UserRepository
import kr.cheaplog.backend.global.error.BusinessException
import kr.cheaplog.backend.global.error.ErrorCode
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class UserService(
    private val userRepository: UserRepository,
    private val keywordRepository: KeywordRepository
) {

    fun getMe(firebaseUid: String): UserResponse {
        val user = userRepository.findByFirebaseUid(firebaseUid)
            .orElseThrow { BusinessException(ErrorCode.USER_NOT_FOUND) }
        val keywordCount = keywordRepository.countByUserId(user.id)
        return UserResponse(
            id = user.id,
            email = user.email,
            nickname = user.nickname,
            keywordCount = keywordCount,
            createdAt = user.createdAt
        )
    }

    @Transactional
    fun updateMe(firebaseUid: String, request: UserUpdateRequest): UserUpdateResponse {
        val user = userRepository.findByFirebaseUid(firebaseUid)
            .orElseThrow { BusinessException(ErrorCode.USER_NOT_FOUND) }
        request.nickname?.let { user.updateNickname(it) }
        request.fcmToken?.let { user.updateFcmToken(it) }
        return UserUpdateResponse(id = user.id, nickname = user.nickname)
    }

    @Transactional
    fun deleteMe(firebaseUid: String) {
        val user = userRepository.findByFirebaseUid(firebaseUid)
            .orElseThrow { BusinessException(ErrorCode.USER_NOT_FOUND) }
        userRepository.delete(user)
    }
}
