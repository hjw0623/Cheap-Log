package kr.cheaplog.backend.domain.hotdeal.dto

import kr.cheaplog.backend.domain.hotdeal.entity.Hotdeal
import java.time.LocalDateTime

data class HotdealListItem(
    val id: Long,
    val source: String,
    val sourceUrl: String,
    val title: String,
    val store: String?,
    val productName: String?,
    val category: String,
    val price: Int?,
    val shippingFee: Int,
    val commentCount: Int,
    val likeCount: Int,
    val isHot: Boolean,
    val isExpired: Boolean,
    val score: Double?,
    val naverLowest: Int?,
    val coupangLink: String?,
    val sourcePostedAt: LocalDateTime?,
    val createdAt: LocalDateTime
) {
    companion object {
        fun from(hotdeal: Hotdeal): HotdealListItem = HotdealListItem(
            id = hotdeal.id,
            source = hotdeal.source,
            sourceUrl = hotdeal.sourceUrl,
            title = hotdeal.title,
            store = hotdeal.store,
            productName = hotdeal.productName,
            category = hotdeal.category,
            price = hotdeal.price,
            shippingFee = hotdeal.shippingFee,
            commentCount = hotdeal.commentCount,
            likeCount = hotdeal.likeCount,
            isHot = hotdeal.isHot,
            isExpired = hotdeal.isExpired,
            score = hotdeal.score?.toDouble(),
            naverLowest = hotdeal.product?.naverLowest,
            coupangLink = hotdeal.product?.coupangLink,
            sourcePostedAt = hotdeal.sourcePostedAt,
            createdAt = hotdeal.createdAt
        )
    }
}
