package kr.cheaplog.backend.domain.product.service.external

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder

@Component
class NaverShoppingClient(
    @Value("\${naver.client-id}") private val clientId: String,
    @Value("\${naver.client-secret}") private val clientSecret: String
) {

    private val log = LoggerFactory.getLogger(javaClass)
    private val restTemplate = RestTemplate()

    data class NaverShoppingResult(
        val lowestPrice: Int?,
        val mallName: String?,
        val link: String?
    )

    fun searchLowestPrice(productName: String): NaverShoppingResult? {
        if (clientId.isBlank() || clientSecret.isBlank()) {
            log.debug("네이버 API 키 미설정 — 건너뜀")
            return null
        }

        return try {
            val uri = UriComponentsBuilder
                .fromUriString("https://openapi.naver.com/v1/search/shop.json")
                .queryParam("query", productName)
                .queryParam("display", 1)
                .queryParam("sort", "asc")
                .build()
                .toUri()

            val headers = HttpHeaders().apply {
                set("X-Naver-Client-Id", clientId)
                set("X-Naver-Client-Secret", clientSecret)
            }

            val response = restTemplate.exchange(
                uri,
                HttpMethod.GET,
                HttpEntity<Void>(headers),
                NaverSearchResponse::class.java
            )

            val item = response.body?.items?.firstOrNull() ?: return null

            NaverShoppingResult(
                lowestPrice = item.lprice?.toIntOrNull(),
                mallName = item.mallName,
                link = item.link
            )
        } catch (e: Exception) {
            log.warn("네이버 쇼핑 API 호출 실패 [query=$productName]: ${e.message}")
            null
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    data class NaverSearchResponse(
        val items: List<NaverSearchItem>? = null
    )

    @JsonIgnoreProperties(ignoreUnknown = true)
    data class NaverSearchItem(
        val lprice: String? = null,
        val mallName: String? = null,
        val link: String? = null
    )
}
