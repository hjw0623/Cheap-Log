package kr.cheaplog.backend.domain.user.repository

import kr.cheaplog.backend.domain.user.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface UserRepository : JpaRepository<User, Long> {
    fun findByFirebaseUid(firebaseUid: String): Optional<User>
    fun existsByFirebaseUid(firebaseUid: String): Boolean
}
