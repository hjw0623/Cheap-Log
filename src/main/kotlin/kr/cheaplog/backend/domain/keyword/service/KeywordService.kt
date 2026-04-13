package kr.cheaplog.backend.domain.keyword.service

import kr.cheaplog.backend.domain.keyword.dto.*
import kr.cheaplog.backend.domain.keyword.entity.Keyword
import kr.cheaplog.backend.domain.keyword.repository.KeywordRepository
import kr.cheaplog.backend.domain.user.repository.UserRepository
import kr.cheaplog.backend.global.error.BusinessException
import kr.cheaplog.backend.global.error.ErrorCode
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class KeywordService(
    private val keywordRepository: KeywordRepository,
    private val userRepository: UserRepository
) {

    companion object {
        private const val MAX_KEYWORDS = 20
        private const val MIN_KEYWORD_LENGTH = 2
        private const val MAX_KEYWORD_LENGTH = 50
    }

    fun getKeywords(firebaseUid: String): KeywordListResponse {
        val user = userRepository.findByFirebaseUid(firebaseUid)
            .orElseThrow { BusinessException(ErrorCode.USER_NOT_FOUND) }
        val keywords = keywordRepository.findByUserIdOrderByCreatedAtDesc(user.id)
        return KeywordListResponse(
            keywords = keywords.map { KeywordResponse.from(it) },
            total = keywords.size
        )
    }

    @Transactional
    fun createKeyword(firebaseUid: String, request: KeywordCreateRequest): KeywordResponse {
        val user = userRepository.findByFirebaseUid(firebaseUid)
            .orElseThrow { BusinessException(ErrorCode.USER_NOT_FOUND) }

        val trimmed = request.keyword.trim()
        if (trimmed.length < MIN_KEYWORD_LENGTH || trimmed.length > MAX_KEYWORD_LENGTH) {
            throw BusinessException(ErrorCode.INVALID_SEARCH_QUERY)
        }

        if (keywordRepository.existsByUserIdAndKeyword(user.id, trimmed)) {
            throw BusinessException(ErrorCode.KEYWORD_DUPLICATE)
        }

        if (keywordRepository.countByUserId(user.id) >= MAX_KEYWORDS) {
            throw BusinessException(ErrorCode.KEYWORD_LIMIT_EXCEEDED)
        }

        val keyword = keywordRepository.save(Keyword(user = user, keyword = trimmed))
        return KeywordResponse.from(keyword)
    }

    @Transactional
    fun updateKeyword(firebaseUid: String, keywordId: Long, request: KeywordUpdateRequest): KeywordResponse {
        val user = userRepository.findByFirebaseUid(firebaseUid)
            .orElseThrow { BusinessException(ErrorCode.USER_NOT_FOUND) }
        val keyword = keywordRepository.findById(keywordId)
            .orElseThrow { BusinessException(ErrorCode.KEYWORD_NOT_FOUND) }

        if (keyword.user.id != user.id) {
            throw BusinessException(ErrorCode.UNAUTHORIZED)
        }

        keyword.toggleActive(request.isActive)
        return KeywordResponse.from(keyword)
    }

    @Transactional
    fun deleteKeyword(firebaseUid: String, keywordId: Long) {
        val user = userRepository.findByFirebaseUid(firebaseUid)
            .orElseThrow { BusinessException(ErrorCode.USER_NOT_FOUND) }
        val keyword = keywordRepository.findById(keywordId)
            .orElseThrow { BusinessException(ErrorCode.KEYWORD_NOT_FOUND) }

        if (keyword.user.id != user.id) {
            throw BusinessException(ErrorCode.UNAUTHORIZED)
        }

        keywordRepository.delete(keyword)
    }
}
