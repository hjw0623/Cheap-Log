package kr.cheaplog.backend.domain.product.service

import kr.cheaplog.backend.domain.product.dto.PriceHistoryItem
import kr.cheaplog.backend.domain.product.dto.PriceHistoryResponse
import kr.cheaplog.backend.domain.product.repository.PriceHistoryRepository
import kr.cheaplog.backend.domain.product.repository.ProductRepository
import kr.cheaplog.backend.global.error.BusinessException
import kr.cheaplog.backend.global.error.ErrorCode
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
@Transactional(readOnly = true)
class ProductService(
    private val productRepository: ProductRepository,
    private val priceHistoryRepository: PriceHistoryRepository
) {

    fun getPriceHistory(productId: Long, days: Int, source: String?): PriceHistoryResponse {
        val product = productRepository.findById(productId)
            .orElseThrow { BusinessException(ErrorCode.PRODUCT_NOT_FOUND) }

        val validDays = days.coerceIn(1, 90)
        val since = LocalDateTime.now().minusDays(validDays.toLong())

        val histories = if (source != null) {
            priceHistoryRepository.findByProductIdAndSourceAndRecordedAtAfterOrderByRecordedAtDesc(
                productId, source, since
            )
        } else {
            priceHistoryRepository.findByProductIdAndRecordedAtAfterOrderByRecordedAtDesc(
                productId, since
            )
        }

        return PriceHistoryResponse(
            productId = product.id,
            productName = product.name,
            histories = histories.map {
                PriceHistoryItem(
                    source = it.source,
                    price = it.price,
                    recordedAt = it.recordedAt
                )
            }
        )
    }
}
