package kr.cheaplog.backend.domain.hotdeal.dto

data class HotdealBatchRequest(
    val items: List<HotdealItem>
)

data class HotdealItem(
    val source: String,
    val sourceId: String,
    val sourceUrl: String,
    val title: String,
    val store: String? = null,
    val productName: String? = null,
    val price: Int? = null,
    val shippingFee: Int? = null,
    val viewCount: Int = 0,
    val commentCount: Int = 0,
    val likeCount: Int = 0,
    val dislikeCount: Int = 0,
    val isHot: Boolean = false,
    val isExpired: Boolean = false,
    val sourcePostedAt: String? = null
)
