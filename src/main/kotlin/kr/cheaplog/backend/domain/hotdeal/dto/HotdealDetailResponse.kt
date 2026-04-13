package kr.cheaplog.backend.domain.hotdeal.dto

import kr.cheaplog.backend.domain.hotdeal.entity.Hotdeal
import java.time.LocalDateTime

data class HotdealDetailResponse(
    val id: Long,
    val source: String,
    val sourceUrl: String,
    val title: String,
    val store: String?,
    val productName: String?,
    val category: String,
    val price: Int?,
    val shippingFee: Int,
    val viewCount: Int,
    val commentCount: Int,
    val likeCount: Int,
    val dislikeCount: Int,
    val isHot: Boolean,
    val isExpired: Boolean,
    val score: Double?,
    val product: ProductInfo?,
    val sourcePostedAt: LocalDateTime?,
    val createdAt: LocalDateTime
) {
    data class ProductInfo(
        val id: Long,
        val name: String,
        val category: String,
        val naverLowest: Int?,
        val naverMall: String?,
        val naverLink: String?,
        val coupangLink: String?,
        val priceUpdatedAt: LocalDateTime?
    )

    companion object {
        fun from(hotdeal: Hotdeal): HotdealDetailResponse = HotdealDetailResponse(
            id = hotdeal.id,
            source = hotdeal.source,
            sourceUrl = hotdeal.sourceUrl,
            title = hotdeal.title,
            store = hotdeal.store,
            productName = hotdeal.productName,
            category = hotdeal.category,
            price = hotdeal.price,
            shippingFee = hotdeal.shippingFee,
            viewCount = hotdeal.viewCount,
            commentCount = hotdeal.commentCount,
            likeCount = hotdeal.likeCount,
            dislikeCount = hotdeal.dislikeCount,
            isHot = hotdeal.isHot,
            isExpired = hotdeal.isExpired,
            score = hotdeal.score?.toDouble(),
            product = hotdeal.product?.let {
                ProductInfo(
                    id = it.id,
                    name = it.name,
                    category = it.category,
                    naverLowest = it.naverLowest,
                    naverMall = it.naverMall,
                    naverLink = it.naverLink,
                    coupangLink = it.coupangLink,
                    priceUpdatedAt = it.priceUpdatedAt
                )
            },
            sourcePostedAt = hotdeal.sourcePostedAt,
            createdAt = hotdeal.createdAt
        )
    }
}
