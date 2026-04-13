package kr.cheaplog.backend.domain.product.entity;

import jakarta.persistence.*;
import kr.cheaplog.backend.global.common.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "products", indexes = {
        @Index(name = "idx_name", columnList = "name"),
        @Index(name = "idx_category", columnList = "category")
})
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 300)
    private String name;

    @Column(length = 30)
    private String category = "etc";

    @Column(name = "naver_lowest")
    private Integer naverLowest;

    @Column(name = "naver_mall", length = 100)
    private String naverMall;

    @Column(name = "naver_link", length = 500)
    private String naverLink;

    @Column(name = "coupang_link", length = 500)
    private String coupangLink;

    @Column(name = "price_updated_at")
    private LocalDateTime priceUpdatedAt;

    @Builder
    public Product(String name, String category) {
        this.name = name;
        this.category = category != null ? category : "etc";
    }

    public void updateNaverPrice(Integer naverLowest, String naverMall, String naverLink) {
        this.naverLowest = naverLowest;
        this.naverMall = naverMall;
        this.naverLink = naverLink;
        this.priceUpdatedAt = LocalDateTime.now();
    }

    public void updateCoupangLink(String coupangLink) {
        this.coupangLink = coupangLink;
    }
}
