package kr.cheaplog.backend.domain.hotdeal.entity;

import jakarta.persistence.*;
import kr.cheaplog.backend.domain.product.entity.Product;
import kr.cheaplog.backend.global.common.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "hotdeals", uniqueConstraints = {
        @UniqueConstraint(name = "uk_source_source_id", columnNames = {"source", "source_id"})
}, indexes = {
        @Index(name = "idx_created_at", columnList = "created_at DESC"),
        @Index(name = "idx_source", columnList = "source"),
        @Index(name = "idx_category", columnList = "category"),
        @Index(name = "idx_score", columnList = "score DESC"),
        @Index(name = "idx_product_id", columnList = "product_id"),
        @Index(name = "idx_is_expired", columnList = "is_expired")
})
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Hotdeal extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 20)
    private String source;

    @Column(name = "source_id", nullable = false, length = 50)
    private String sourceId;

    @Column(name = "source_url", nullable = false, length = 500)
    private String sourceUrl;

    @Column(nullable = false, length = 500)
    private String title;

    @Column(length = 50)
    private String store;

    @Column(name = "product_name", length = 300)
    private String productName;

    @Column(length = 30)
    private String category = "etc";

    private Integer price;

    @Column(name = "shipping_fee")
    private Integer shippingFee = 0;

    @Column(name = "view_count", nullable = false)
    private int viewCount = 0;

    @Column(name = "comment_count", nullable = false)
    private int commentCount = 0;

    @Column(name = "like_count", nullable = false)
    private int likeCount = 0;

    @Column(name = "dislike_count", nullable = false)
    private int dislikeCount = 0;

    @Column(name = "is_hot", nullable = false)
    private boolean isHot = false;

    @Column(name = "is_expired", nullable = false)
    private boolean isExpired = false;

    @Column(precision = 5, scale = 2)
    private BigDecimal score;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", foreignKey = @ForeignKey(name = "fk_hotdeals_product"))
    private Product product;

    @Column(name = "source_posted_at")
    private LocalDateTime sourcePostedAt;

    @Builder
    public Hotdeal(String source, String sourceId, String sourceUrl, String title,
                   String store, String productName, String category,
                   Integer price, Integer shippingFee,
                   int viewCount, int commentCount, int likeCount, int dislikeCount,
                   boolean isHot, boolean isExpired,
                   LocalDateTime sourcePostedAt) {
        this.source = source;
        this.sourceId = sourceId;
        this.sourceUrl = sourceUrl;
        this.title = title;
        this.store = store;
        this.productName = productName;
        this.category = category != null ? category : "etc";
        this.price = price;
        this.shippingFee = shippingFee != null ? shippingFee : 0;
        this.viewCount = viewCount;
        this.commentCount = commentCount;
        this.likeCount = likeCount;
        this.dislikeCount = dislikeCount;
        this.isHot = isHot;
        this.isExpired = isExpired;
        this.sourcePostedAt = sourcePostedAt;
    }

    public void updateCommunityStats(int viewCount, int commentCount, int likeCount, int dislikeCount,
                                     boolean isHot, boolean isExpired) {
        this.viewCount = viewCount;
        this.commentCount = commentCount;
        this.likeCount = likeCount;
        this.dislikeCount = dislikeCount;
        this.isHot = isHot;
        this.isExpired = isExpired;
    }

    public void updateScore(BigDecimal score) {
        this.score = score;
    }

    public void linkProduct(Product product) {
        this.product = product;
    }
}
