package kr.cheaplog.backend.domain.keyword.entity;

import jakarta.persistence.*;
import kr.cheaplog.backend.domain.user.entity.User;
import kr.cheaplog.backend.global.common.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "keywords", uniqueConstraints = {
        @UniqueConstraint(name = "uk_user_keyword", columnNames = {"user_id", "keyword"})
}, indexes = {
        @Index(name = "idx_active_keyword", columnList = "is_active, keyword")
})
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Keyword extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_keywords_user"))
    private User user;

    @Column(nullable = false, length = 50)
    private String keyword;

    @Column(name = "is_active", nullable = false)
    private boolean isActive = true;

    @Builder
    public Keyword(User user, String keyword) {
        this.user = user;
        this.keyword = keyword;
        this.isActive = true;
    }

    public void toggleActive(boolean isActive) {
        this.isActive = isActive;
    }
}
