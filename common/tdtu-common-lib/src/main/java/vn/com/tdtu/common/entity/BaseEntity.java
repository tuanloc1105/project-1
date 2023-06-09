package vn.com.tdtu.common.entity;

import jakarta.persistence.*;
import lombok.Data;
import vn.com.tdtu.common.constant.CommonConstant;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@MappedSuperclass
@Data
public abstract class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "ACTIVE", nullable = false)
    private boolean active = true;

    @Column(name = "CREATED_AT", nullable = false, updatable = false)
    private LocalDateTime createdAt = Instant.ofEpochMilli(System.currentTimeMillis()).atZone(ZoneId.of(CommonConstant.DEFAULT_TIMEZONE)).toLocalDateTime();

    @Column(name = "UPDATED_AT", nullable = false)
    private LocalDateTime updatedAt = Instant.ofEpochMilli(System.currentTimeMillis()).atZone(ZoneId.of(CommonConstant.DEFAULT_TIMEZONE)).toLocalDateTime();

    @Column(name = "CREATED_BY", updatable = false)
    private String createdBy;

    @Column(name = "UPDATED_BY")
    private String updatedBy;

    public BaseEntity() {
    }

    public BaseEntity(Long id, boolean active, LocalDateTime createdAt, LocalDateTime updatedAt, String createdBy, String updatedBy) {
        this.id = id;
        this.active = active;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.createdBy = createdBy;
        this.updatedBy = updatedBy;
    }
}
