package kr.cheaplog.backend.domain.product.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "price_history", indexes = {
        @Index(name = "idx_product_recorded", columnList = "product_id, recorded_at DESC"),
        @Index(name = "idx_product_source", columnList = "product_id, source")
})
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PriceHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_price_history_product"))
    private Product product;

    @Column(nullable = false, length = 20)
    private String source;

    @Column(nullable = false)
    private int price;

    @Column(name = "recorded_at", nullable = false)
    private LocalDateTime recordedAt;

    @Builder
    public PriceHistory(Product product, String source, int price, LocalDateTime recordedAt) {
        this.product = product;
        this.source = source;
        this.price = price;
        this.recordedAt = recordedAt;
    }
}
