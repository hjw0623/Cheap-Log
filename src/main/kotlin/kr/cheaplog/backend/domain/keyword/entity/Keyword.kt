package kr.cheaplog.backend.domain.keyword.entity

import jakarta.persistence.*
import kr.cheaplog.backend.domain.user.entity.User
import kr.cheaplog.backend.global.common.BaseTimeEntity

@Entity
@Table(
    name = "keywords",
    uniqueConstraints = [
        UniqueConstraint(name = "uk_user_keyword", columnNames = ["user_id", "keyword"])
    ],
    indexes = [
        Index(name = "idx_active_keyword", columnList = "is_active, keyword")
    ]
)
class Keyword(
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "user_id", nullable = false,
        foreignKey = ForeignKey(name = "fk_keywords_user")
    )
    val user: User,

    @Column(nullable = false, length = 50)
    val keyword: String,

    @Column(name = "is_active", nullable = false)
    var isActive: Boolean = true,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L
) : BaseTimeEntity() {

    fun toggleActive(active: Boolean) {
        this.isActive = active
    }
}
