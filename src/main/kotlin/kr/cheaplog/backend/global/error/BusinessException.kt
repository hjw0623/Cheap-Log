package kr.cheaplog.backend.global.error

class BusinessException(
    val errorCode: ErrorCode
) : RuntimeException(errorCode.message)
