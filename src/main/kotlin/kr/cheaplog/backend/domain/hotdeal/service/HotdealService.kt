package kr.cheaplog.backend.domain.hotdeal.service

import kr.cheaplog.backend.domain.hotdeal.dto.HotdealDetailResponse
import kr.cheaplog.backend.domain.hotdeal.dto.HotdealListItem
import kr.cheaplog.backend.domain.hotdeal.repository.HotdealRepository
import kr.cheaplog.backend.global.dto.CursorResponse
import kr.cheaplog.backend.global.error.BusinessException
import kr.cheaplog.backend.global.error.ErrorCode
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class HotdealService(
    private val hotdealRepository: HotdealRepository
) {

    fun getList(
        cursor: Long?,
        size: Int,
        source: String?,
        category: String?,
        isExpired: Boolean,
        sort: String
    ): CursorResponse<HotdealListItem> {
        val fetchSize = size + 1
        val pageable = PageRequest.of(0, fetchSize)

        val hotdeals = when (sort) {
            "score" -> hotdealRepository.findByScore(isExpired, source, category, cursor, pageable)
            "popular" -> hotdealRepository.findByPopular(isExpired, source, category, cursor, pageable)
            else -> hotdealRepository.findByLatest(isExpired, source, category, cursor, pageable)
        }

        val hasNext = hotdeals.size > size
        val items = hotdeals.take(size).map { HotdealListItem.from(it) }
        val nextCursor = if (hasNext) items.lastOrNull()?.id else null

        return CursorResponse(items = items, nextCursor = nextCursor, hasNext = hasNext)
    }

    fun getDetail(id: Long): HotdealDetailResponse {
        val hotdeal = hotdealRepository.findById(id)
            .orElseThrow { BusinessException(ErrorCode.HOTDEAL_NOT_FOUND) }
        return HotdealDetailResponse.from(hotdeal)
    }

    fun search(query: String, cursor: Long?, size: Int): CursorResponse<HotdealListItem> {
        if (query.length < 2) throw BusinessException(ErrorCode.INVALID_SEARCH_QUERY)

        val fetchSize = size + 1
        val hotdeals = hotdealRepository.searchByFullText(query, cursor, fetchSize)

        val hasNext = hotdeals.size > size
        val items = hotdeals.take(size).map { HotdealListItem.from(it) }
        val nextCursor = if (hasNext) items.lastOrNull()?.id else null

        return CursorResponse(items = items, nextCursor = nextCursor, hasNext = hasNext)
    }
}
