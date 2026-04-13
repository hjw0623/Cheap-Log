package kr.cheaplog.backend.domain.product.entity

import jakarta.persistence.*
import kr.cheaplog.backend.global.common.BaseTimeEntity
import java.time.LocalDateTime

@Entity
@Table(
    name = "products",
    indexes = [
        Index(name = "idx_name", columnList = "name"),
        Index(name = "idx_category", columnList = "category")
    ]
)
class Product(
    @Column(nullable = false, length = 300)
    val name: String,

    @Column(length = 30)
    var category: String = "etc",

    @Column(name = "naver_lowest")
    var naverLowest: Int? = null,

    @Column(name = "naver_mall", length = 100)
    var naverMall: String? = null,

    @Column(name = "naver_link", length = 500)
    var naverLink: String? = null,

    @Column(name = "coupang_link", length = 500)
    var coupangLink: String? = null,

    @Column(name = "price_updated_at")
    var priceUpdatedAt: LocalDateTime? = null,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L
) : BaseTimeEntity() {

    fun updateNaverPrice(naverLowest: Int?, naverMall: String?, naverLink: String?) {
        this.naverLowest = naverLowest
        this.naverMall = naverMall
        this.naverLink = naverLink
        this.priceUpdatedAt = LocalDateTime.now()
    }

    fun updateCoupangLink(coupangLink: String?) {
        this.coupangLink = coupangLink
    }
}
