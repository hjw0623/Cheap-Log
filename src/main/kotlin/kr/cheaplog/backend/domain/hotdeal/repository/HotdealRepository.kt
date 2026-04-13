package kr.cheaplog.backend.domain.hotdeal.repository

import kr.cheaplog.backend.domain.hotdeal.entity.Hotdeal
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface HotdealRepository : JpaRepository<Hotdeal, Long> {
    fun findBySourceAndSourceId(source: String, sourceId: String): Optional<Hotdeal>
}
