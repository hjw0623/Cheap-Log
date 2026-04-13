package kr.cheaplog.backend.domain.crawllog.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "crawl_logs", indexes = {
        @Index(name = "idx_source_started", columnList = "source, started_at DESC"),
        @Index(name = "idx_status", columnList = "status")
})
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CrawlLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 20)
    private String source;

    @Column(nullable = false, length = 10)
    private String status;

    @Column(name = "collected_count")
    private Integer collectedCount = 0;

    @Column(name = "new_count")
    private Integer newCount = 0;

    @Column(name = "parse_fail_count")
    private Integer parseFailCount = 0;

    @Column(name = "error_message", columnDefinition = "TEXT")
    private String errorMessage;

    @Column(name = "started_at", nullable = false)
    private LocalDateTime startedAt;

    @Builder
    public CrawlLog(String source, String status,
                    Integer collectedCount, Integer newCount, Integer parseFailCount,
                    String errorMessage) {
        this.source = source;
        this.status = status;
        this.collectedCount = collectedCount != null ? collectedCount : 0;
        this.newCount = newCount != null ? newCount : 0;
        this.parseFailCount = parseFailCount != null ? parseFailCount : 0;
        this.errorMessage = errorMessage;
        this.startedAt = LocalDateTime.now();
    }
}
