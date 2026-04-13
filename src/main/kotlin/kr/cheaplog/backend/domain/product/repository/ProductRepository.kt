package kr.cheaplog.backend.domain.product.repository

import kr.cheaplog.backend.domain.product.entity.Product
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface ProductRepository : JpaRepository<Product, Long> {

    @Query("SELECT p FROM Product p WHERE p.name LIKE %:name%")
    fun findByNameContaining(name: String): List<Product>
}
