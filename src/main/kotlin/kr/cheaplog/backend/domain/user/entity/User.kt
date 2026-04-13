package kr.cheaplog.backend.domain.user.entity

import jakarta.persistence.*
import kr.cheaplog.backend.global.common.BaseTimeEntity

@Entity
@Table(
    name = "users",
    uniqueConstraints = [
        UniqueConstraint(name = "uk_firebase_uid", columnNames = ["firebase_uid"]),
        UniqueConstraint(name = "uk_email", columnNames = ["email"])
    ]
)
class User(
    @Column(name = "firebase_uid", nullable = false, length = 128)
    val firebaseUid: String,

    @Column(nullable = false, length = 255)
    val email: String,

    @Column(nullable = false, length = 50)
    var nickname: String,

    @Column(name = "fcm_token", length = 512)
    var fcmToken: String? = null,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L
) : BaseTimeEntity() {

    fun updateNickname(nickname: String) {
        this.nickname = nickname
    }

    fun updateFcmToken(fcmToken: String?) {
        this.fcmToken = fcmToken
    }

    fun clearFcmToken() {
        this.fcmToken = null
    }
}
