package kr.cheaplog.backend.global.dto

import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
data class ApiResponse<T>(
    val success: Boolean,
    val data: T? = null,
    val message: String? = null,
    val code: String? = null
) {
    companion object {
        fun <T> ok(data: T? = null): ApiResponse<T> =
            ApiResponse(success = true, data = data)

        fun error(code: String, message: String): ApiResponse<Nothing> =
            ApiResponse(success = false, message = message, code = code)
    }
}
