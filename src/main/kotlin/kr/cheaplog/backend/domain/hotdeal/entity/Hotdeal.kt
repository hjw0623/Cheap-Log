package kr.cheaplog.backend.domain.hotdeal.entity

import jakarta.persistence.*
import kr.cheaplog.backend.domain.product.entity.Product
import kr.cheaplog.backend.global.common.BaseTimeEntity
import java.math.BigDecimal
import java.time.LocalDateTime

@Entity
@Table(
    name = "hotdeals",
    uniqueConstraints = [
        UniqueConstraint(name = "uk_source_source_id", columnNames = ["source", "source_id"])
    ],
    indexes = [
        Index(name = "idx_created_at", columnList = "created_at DESC"),
        Index(name = "idx_source", columnList = "source"),
        Index(name = "idx_category", columnList = "category"),
        Index(name = "idx_score", columnList = "score DESC"),
        Index(name = "idx_product_id", columnList = "product_id"),
        Index(name = "idx_is_expired", columnList = "is_expired")
    ]
)
class Hotdeal(
    @Column(nullable = false, length = 20)
    val source: String,

    @Column(name = "source_id", nullable = false, length = 50)
    val sourceId: String,

    @Column(name = "source_url", nullable = false, length = 500)
    val sourceUrl: String,

    @Column(nullable = false, length = 500)
    val title: String,

    @Column(length = 50)
    var store: String? = null,

    @Column(name = "product_name", length = 300)
    var productName: String? = null,

    @Column(length = 30)
    var category: String = "etc",

    var price: Int? = null,

    @Column(name = "shipping_fee")
    var shippingFee: Int = 0,

    @Column(name = "view_count", nullable = false)
    var viewCount: Int = 0,

    @Column(name = "comment_count", nullable = false)
    var commentCount: Int = 0,

    @Column(name = "like_count", nullable = false)
    var likeCount: Int = 0,

    @Column(name = "dislike_count", nullable = false)
    var dislikeCount: Int = 0,

    @Column(name = "is_hot", nullable = false)
    var isHot: Boolean = false,

    @Column(name = "is_expired", nullable = false)
    var isExpired: Boolean = false,

    @Column(precision = 5, scale = 2)
    var score: BigDecimal? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", foreignKey = ForeignKey(name = "fk_hotdeals_product"))
    var product: Product? = null,

    @Column(name = "source_posted_at")
    var sourcePostedAt: LocalDateTime? = null,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L
) : BaseTimeEntity() {

    fun updateCommunityStats(
        viewCount: Int,
        commentCount: Int,
        likeCount: Int,
        dislikeCount: Int,
        isHot: Boolean,
        isExpired: Boolean
    ) {
        this.viewCount = viewCount
        this.commentCount = commentCount
        this.likeCount = likeCount
        this.dislikeCount = dislikeCount
        this.isHot = isHot
        this.isExpired = isExpired
    }

    fun updateScore(score: BigDecimal) {
        this.score = score
    }

    fun linkProduct(product: Product) {
        this.product = product
    }
}
