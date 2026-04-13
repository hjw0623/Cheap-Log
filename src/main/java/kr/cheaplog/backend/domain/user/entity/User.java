package kr.cheaplog.backend.domain.user.entity;

import jakarta.persistence.*;
import kr.cheaplog.backend.global.common.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(name = "uk_firebase_uid", columnNames = "firebase_uid"),
        @UniqueConstraint(name = "uk_email", columnNames = "email")
})
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "firebase_uid", nullable = false, length = 128)
    private String firebaseUid;

    @Column(nullable = false, length = 255)
    private String email;

    @Column(nullable = false, length = 50)
    private String nickname;

    @Column(name = "fcm_token", length = 512)
    private String fcmToken;

    @Builder
    public User(String firebaseUid, String email, String nickname, String fcmToken) {
        this.firebaseUid = firebaseUid;
        this.email = email;
        this.nickname = nickname;
        this.fcmToken = fcmToken;
    }

    public void updateNickname(String nickname) {
        this.nickname = nickname;
    }

    public void updateFcmToken(String fcmToken) {
        this.fcmToken = fcmToken;
    }

    public void clearFcmToken() {
        this.fcmToken = null;
    }
}
