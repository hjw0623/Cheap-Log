package kr.cheaplog.backend.domain.notification.service

import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.Message
import com.google.firebase.messaging.Notification
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class FcmService {

    private val log = LoggerFactory.getLogger(javaClass)

    fun sendPush(fcmToken: String, title: String, body: String, data: Map<String, String> = emptyMap()) {
        try {
            val message = Message.builder()
                .setToken(fcmToken)
                .setNotification(
                    Notification.builder()
                        .setTitle(title)
                        .setBody(body)
                        .build()
                )
                .putAllData(data)
                .build()

            FirebaseMessaging.getInstance().send(message)
        } catch (e: Exception) {
            log.warn("FCM 발송 실패 [token=${fcmToken.take(10)}...]: ${e.message}")
        }
    }
}
