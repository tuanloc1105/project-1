package vn.com.tdtu.document.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.com.tdtu.common.entity.BaseEntity;
import vn.com.tdtu.document.enums.DocumentStatus;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@NoArgsConstructor
@Data
@Entity
@Table(name = "DOCUMENT")
public class Document extends BaseEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = -601735217938278902L;

    @Column(name = "DOCUMENT_ID")
    private String documentId;

    @Column(name = "LOAN_AMOUNT")
    private BigDecimal loanAmount;

    @Column(name = "FULL_NAME")
    private String fullName;

    @Column(name = "ID_CARD_NO")
    private String idCardNo;

    @Column(name = "PHONE_NUMBER")
    private String phoneNumber;

    @Column(name = "JOB_NAME")
    private String jobName;

    @Column(name = "COLLATERAL")
    private String collateral;

    @Column(name = "LOAN_REASON")
    private String loanReason;

    @Column(name = "STATUS")
    @Enumerated(EnumType.STRING)
    private DocumentStatus status;

    @Column(name = "STATUS_CHANGED_BY")
    private String statusChangedBy;

    @Column(name = "REASON")
    private String reason;

    @Builder
    public Document(Long id, boolean active, LocalDateTime createdAt, LocalDateTime updatedAt, String createdBy, String updatedBy, BigDecimal loanAmount, String fullName, String idCardNo, String phoneNumber, String jobName, String collateral, String loanReason, DocumentStatus status, String statusChangedBy) {
        super(id, active, createdAt, updatedAt, createdBy, updatedBy);
        this.loanAmount = loanAmount;
        this.fullName = fullName;
        this.idCardNo = idCardNo;
        this.phoneNumber = phoneNumber;
        this.jobName = jobName;
        this.collateral = collateral;
        this.loanReason = loanReason;
        this.status = status;
        this.statusChangedBy = statusChangedBy;
    }
}
