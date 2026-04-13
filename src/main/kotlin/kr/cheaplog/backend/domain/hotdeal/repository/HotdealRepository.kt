package kr.cheaplog.backend.domain.hotdeal.repository

import kr.cheaplog.backend.domain.hotdeal.entity.Hotdeal
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.Optional

interface HotdealRepository : JpaRepository<Hotdeal, Long> {

    fun findBySourceAndSourceId(source: String, sourceId: String): Optional<Hotdeal>

    // 커서 기반 페이지네이션 - 최신순
    @Query("""
        SELECT h FROM Hotdeal h
        LEFT JOIN FETCH h.product
        WHERE (:isExpired = true OR h.isExpired = false)
          AND (:source IS NULL OR h.source = :source)
          AND (:category IS NULL OR h.category = :category)
          AND (:cursor IS NULL OR h.id < :cursor)
        ORDER BY h.id DESC
    """)
    fun findByLatest(
        isExpired: Boolean,
        source: String?,
        category: String?,
        cursor: Long?,
        pageable: Pageable
    ): List<Hotdeal>

    // 커서 기반 페이지네이션 - 점수순
    @Query("""
        SELECT h FROM Hotdeal h
        LEFT JOIN FETCH h.product
        WHERE (:isExpired = true OR h.isExpired = false)
          AND (:source IS NULL OR h.source = :source)
          AND (:category IS NULL OR h.category = :category)
          AND (:cursor IS NULL OR h.id < :cursor)
        ORDER BY h.score DESC NULLS LAST, h.id DESC
    """)
    fun findByScore(
        isExpired: Boolean,
        source: String?,
        category: String?,
        cursor: Long?,
        pageable: Pageable
    ): List<Hotdeal>

    // 커서 기반 페이지네이션 - 인기순
    @Query("""
        SELECT h FROM Hotdeal h
        LEFT JOIN FETCH h.product
        WHERE (:isExpired = true OR h.isExpired = false)
          AND (:source IS NULL OR h.source = :source)
          AND (:category IS NULL OR h.category = :category)
          AND (:cursor IS NULL OR h.id < :cursor)
        ORDER BY h.likeCount DESC, h.id DESC
    """)
    fun findByPopular(
        isExpired: Boolean,
        source: String?,
        category: String?,
        cursor: Long?,
        pageable: Pageable
    ): List<Hotdeal>

    // FULLTEXT 검색 (MySQL native query)
    @Query(
        value = """
            SELECT h.* FROM hotdeals h
            LEFT JOIN products p ON h.product_id = p.id
            WHERE MATCH(h.title, h.product_name) AGAINST(:query IN BOOLEAN MODE)
              AND (:cursor IS NULL OR h.id < :cursor)
            ORDER BY h.id DESC
            LIMIT :size
        """,
        nativeQuery = true
    )
    fun searchByFullText(query: String, cursor: Long?, size: Int): List<Hotdeal>
}
