package kr.cheaplog.backend.domain.hotdeal.service

import kr.cheaplog.backend.domain.hotdeal.dto.*
import kr.cheaplog.backend.domain.hotdeal.entity.Hotdeal
import kr.cheaplog.backend.domain.hotdeal.repository.HotdealRepository
import kr.cheaplog.backend.domain.product.entity.Product
import kr.cheaplog.backend.domain.product.repository.ProductRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
@Transactional
class InternalHotdealService(
    private val hotdealRepository: HotdealRepository,
    private val productRepository: ProductRepository,
    private val categoryClassifier: HotdealCategoryClassifier,
    private val scoreCalculator: HotdealScoreCalculator
) {

    fun processBatch(request: HotdealBatchRequest): HotdealBatchResponse {
        var newCount = 0
        var updatedCount = 0
        val results = mutableListOf<HotdealBatchResult>()

        for (item in request.items) {
            val existing = hotdealRepository.findBySourceAndSourceId(item.source, item.sourceId)

            if (existing.isPresent) {
                val hotdeal = existing.get()
                hotdeal.updateCommunityStats(
                    viewCount = item.viewCount,
                    commentCount = item.commentCount,
                    likeCount = item.likeCount,
                    dislikeCount = item.dislikeCount,
                    isHot = item.isHot,
                    isExpired = item.isExpired
                )
                hotdeal.updateScore(scoreCalculator.calculate(hotdeal))
                updatedCount++
                results.add(HotdealBatchResult(item.sourceId, hotdeal.id, isNew = false))
            } else {
                val category = categoryClassifier.classify(item.productName)
                val product = linkOrCreateProduct(item.productName, category)

                val hotdeal = hotdealRepository.save(
                    Hotdeal(
                        source = item.source,
                        sourceId = item.sourceId,
                        sourceUrl = item.sourceUrl,
                        title = item.title,
                        store = item.store,
                        productName = item.productName,
                        category = category,
                        price = item.price,
                        shippingFee = item.shippingFee ?: 0,
                        viewCount = item.viewCount,
                        commentCount = item.commentCount,
                        likeCount = item.likeCount,
                        dislikeCount = item.dislikeCount,
                        isHot = item.isHot,
                        isExpired = item.isExpired,
                        sourcePostedAt = item.sourcePostedAt?.let { parseDateTime(it) }
                    )
                )
                product?.let { hotdeal.linkProduct(it) }
                hotdeal.updateScore(scoreCalculator.calculate(hotdeal))

                newCount++
                results.add(HotdealBatchResult(item.sourceId, hotdeal.id, isNew = true))
            }
        }

        return HotdealBatchResponse(
            totalCount = request.items.size,
            newCount = newCount,
            updatedCount = updatedCount,
            results = results
        )
    }

    private fun linkOrCreateProduct(productName: String?, category: String): Product? {
        if (productName.isNullOrBlank()) return null

        val existing = productRepository.findByNameContaining(productName)
        if (existing.isNotEmpty()) return existing.first()

        return productRepository.save(Product(name = productName, category = category))
    }

    private fun parseDateTime(isoString: String): LocalDateTime? {
        return try {
            LocalDateTime.parse(isoString)
        } catch (e: Exception) {
            null
        }
    }
}
