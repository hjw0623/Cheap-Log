package kr.cheaplog.backend.global.error

import org.springframework.http.HttpStatus

enum class ErrorCode(
    val status: HttpStatus,
    val code: String,
    val message: String
) {
    // 인증
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "INVALID_TOKEN", "Firebase 토큰 검증 실패"),
    TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "TOKEN_EXPIRED", "토큰이 만료되었습니다"),
    UNAUTHORIZED(HttpStatus.FORBIDDEN, "UNAUTHORIZED", "권한이 없습니다"),
    INTERNAL_KEY_INVALID(HttpStatus.UNAUTHORIZED, "INTERNAL_KEY_INVALID", "내부 API Key가 올바르지 않습니다"),

    // 리소스 없음
    HOTDEAL_NOT_FOUND(HttpStatus.NOT_FOUND, "HOTDEAL_NOT_FOUND", "핫딜을 찾을 수 없습니다"),
    PRODUCT_NOT_FOUND(HttpStatus.NOT_FOUND, "PRODUCT_NOT_FOUND", "상품을 찾을 수 없습니다"),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "USER_NOT_FOUND", "사용자를 찾을 수 없습니다"),
    KEYWORD_NOT_FOUND(HttpStatus.NOT_FOUND, "KEYWORD_NOT_FOUND", "키워드를 찾을 수 없습니다"),

    // 비즈니스 룰
    KEYWORD_DUPLICATE(HttpStatus.CONFLICT, "KEYWORD_DUPLICATE", "이미 등록된 키워드입니다"),
    KEYWORD_LIMIT_EXCEEDED(HttpStatus.BAD_REQUEST, "KEYWORD_LIMIT_EXCEEDED", "키워드는 최대 20개까지 등록 가능합니다"),
    INVALID_SEARCH_QUERY(HttpStatus.BAD_REQUEST, "INVALID_SEARCH_QUERY", "검색어는 최소 2자 이상이어야 합니다"),

    // 서버
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_SERVER_ERROR", "서버 내부 오류가 발생했습니다");
}
