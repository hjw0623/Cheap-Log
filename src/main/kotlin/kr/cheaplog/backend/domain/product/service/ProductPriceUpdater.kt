package kr.cheaplog.backend.domain.product.service

import kr.cheaplog.backend.domain.product.entity.PriceHistory
import kr.cheaplog.backend.domain.product.entity.Product
import kr.cheaplog.backend.domain.product.repository.PriceHistoryRepository
import kr.cheaplog.backend.domain.product.repository.ProductRepository
import kr.cheaplog.backend.domain.product.service.external.NaverShoppingClient
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
class ProductPriceUpdater(
    private val productRepository: ProductRepository,
    private val priceHistoryRepository: PriceHistoryRepository,
    private val naverShoppingClient: NaverShoppingClient
) {

    private val log = LoggerFactory.getLogger(javaClass)

    /**
     * 상품의 네이버 최저가를 비동기로 조회하고 업데이트한다.
     * 조회 성공 시 price_history에도 기록한다.
     */
    @Async
    @Transactional
    fun updateNaverPrice(product: Product) {
        val result = naverShoppingClient.searchLowestPrice(product.name) ?: return

        product.updateNaverPrice(
            naverLowest = result.lowestPrice,
            naverMall = result.mallName,
            naverLink = result.link
        )
        productRepository.save(product)

        result.lowestPrice?.let { price ->
            priceHistoryRepository.save(
                PriceHistory(
                    product = product,
                    source = "naver",
                    price = price,
                    recordedAt = LocalDateTime.now()
                )
            )
        }

        log.info("네이버 최저가 갱신 [product=${product.id}, price=${result.lowestPrice}]")
    }
}
