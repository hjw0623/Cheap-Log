package kr.cheaplog.backend.domain.hotdeal.service

import kr.cheaplog.backend.domain.hotdeal.entity.Hotdeal
import org.springframework.stereotype.Component
import java.math.BigDecimal
import java.math.RoundingMode
import java.time.Duration
import java.time.LocalDateTime
import kotlin.math.ln
import kotlin.math.max
import kotlin.math.min

/**
 * 핫딜 점수 산출기.
 *
 * | 요소           | 비중 | 산출 기준                                      |
 * |---------------|------|----------------------------------------------|
 * | 할인율         | 40%  | (naverLowest - price) / naverLowest * 100     |
 * | 커뮤니티 반응   | 30%  | 추천수, 댓글수, 조회수를 정규화                    |
 * | 시간 감쇠       | 20%  | 게시 후 시간 경과에 따라 점수 차감                  |
 * | 핫글 보너스     | 10%  | isHot = true일 경우 가산점                       |
 *
 * naverLowest가 없으면 할인율 요소를 제외하고 나머지 요소로 정규화하여 계산.
 */
@Component
class HotdealScoreCalculator {

    fun calculate(hotdeal: Hotdeal): BigDecimal {
        val naverLowest = hotdeal.product?.naverLowest
        val hasDiscount = naverLowest != null && naverLowest > 0 && hotdeal.price != null && hotdeal.price!! > 0

        val discountScore = if (hasDiscount) {
            calculateDiscountScore(hotdeal.price!!, naverLowest!!)
        } else {
            0.0
        }

        val communityScore = calculateCommunityScore(
            likeCount = hotdeal.likeCount,
            commentCount = hotdeal.commentCount,
            viewCount = hotdeal.viewCount
        )

        val timeDecayScore = calculateTimeDecay(hotdeal.sourcePostedAt ?: hotdeal.createdAt)

        val hotBonusScore = if (hotdeal.isHot) 100.0 else 0.0

        val totalScore = if (hasDiscount) {
            discountScore * 0.40 +
            communityScore * 0.30 +
            timeDecayScore * 0.20 +
            hotBonusScore * 0.10
        } else {
            // 할인율 제외 시 나머지 비중을 정규화 (30+20+10=60 → 100)
            communityScore * (30.0 / 60.0) +
            timeDecayScore * (20.0 / 60.0) +
            hotBonusScore * (10.0 / 60.0)
        }

        return BigDecimal(min(totalScore, 100.0)).setScale(2, RoundingMode.HALF_UP)
    }

    /** 할인율 점수 (0~100). 50% 이상 할인이면 만점. */
    private fun calculateDiscountScore(dealPrice: Int, naverLowest: Int): Double {
        val discountRate = (naverLowest - dealPrice).toDouble() / naverLowest * 100
        return min(max(discountRate * 2, 0.0), 100.0)
    }

    /**
     * 커뮤니티 반응 점수 (0~100).
     * 로그 스케일로 정규화하여 극단값 영향을 완화한다.
     */
    private fun calculateCommunityScore(likeCount: Int, commentCount: Int, viewCount: Int): Double {
        val likeScore = min(logNormalize(likeCount, 500) * 50, 50.0)
        val commentScore = min(logNormalize(commentCount, 100) * 30, 30.0)
        val viewScore = min(logNormalize(viewCount, 10000) * 20, 20.0)
        return likeScore + commentScore + viewScore
    }

    /** 시간 감쇠 점수 (0~100). 48시간 후 0점. */
    private fun calculateTimeDecay(postedAt: LocalDateTime): Double {
        val hoursElapsed = Duration.between(postedAt, LocalDateTime.now()).toHours().toDouble()
        val decayHours = 48.0
        return max((1.0 - hoursElapsed / decayHours) * 100, 0.0)
    }

    private fun logNormalize(value: Int, maxRef: Int): Double {
        if (value <= 0) return 0.0
        return min(ln(value.toDouble() + 1) / ln(maxRef.toDouble() + 1), 1.0)
    }
}
