package kr.cheaplog.backend.domain.product.entity

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(
    name = "price_history",
    indexes = [
        Index(name = "idx_product_recorded", columnList = "product_id, recorded_at DESC"),
        Index(name = "idx_product_source", columnList = "product_id, source")
    ]
)
class PriceHistory(
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "product_id", nullable = false,
        foreignKey = ForeignKey(name = "fk_price_history_product")
    )
    val product: Product,

    @Column(nullable = false, length = 20)
    val source: String,

    @Column(nullable = false)
    val price: Int,

    @Column(name = "recorded_at", nullable = false)
    val recordedAt: LocalDateTime,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L
)
