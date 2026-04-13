package kr.cheaplog.backend.domain.notification.service

import kr.cheaplog.backend.domain.hotdeal.entity.Hotdeal
import kr.cheaplog.backend.domain.keyword.repository.KeywordRepository
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service

@Service
class KeywordNotificationService(
    private val keywordRepository: KeywordRepository,
    private val fcmService: FcmService
) {

    private val log = LoggerFactory.getLogger(javaClass)

    /**
     * 신규 핫딜에 매칭되는 활성 키워드를 찾아 해당 사용자에게 FCM 푸시를 발송한다.
     * 비동기로 실행하여 핫딜 저장 응답 속도에 영향을 주지 않는다.
     */
    @Async
    fun matchAndNotify(hotdeals: List<Hotdeal>) {
        val activeKeywords = keywordRepository.findByIsActiveTrue()
        if (activeKeywords.isEmpty()) return

        for (hotdeal in hotdeals) {
            val titleLower = hotdeal.title.lowercase()
            val productNameLower = hotdeal.productName?.lowercase() ?: ""

            val matched = activeKeywords.filter { kw ->
                val keywordLower = kw.keyword.lowercase()
                titleLower.contains(keywordLower) || productNameLower.contains(keywordLower)
            }

            // 사용자별로 중복 알림 방지 (같은 핫딜에 여러 키워드 매칭돼도 1회만 발송)
            val notifiedUsers = mutableSetOf<Long>()

            for (keyword in matched) {
                val user = keyword.user
                if (user.id in notifiedUsers) continue
                if (user.fcmToken.isNullOrBlank()) continue

                fcmService.sendPush(
                    fcmToken = user.fcmToken!!,
                    title = "🔔 키워드 알림: ${keyword.keyword}",
                    body = hotdeal.title,
                    data = mapOf(
                        "hotdealId" to hotdeal.id.toString(),
                        "keyword" to keyword.keyword
                    )
                )
                notifiedUsers.add(user.id)
                log.info("FCM 발송 [user=${user.id}, keyword=${keyword.keyword}, hotdeal=${hotdeal.id}]")
            }
        }
    }
}
