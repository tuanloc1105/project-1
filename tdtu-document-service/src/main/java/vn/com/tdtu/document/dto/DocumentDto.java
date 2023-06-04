package vn.com.tdtu.document.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.com.tdtu.document.enums.DocumentStatus;

import java.math.BigDecimal;

@AllArgsConstructor
@Data
@NoArgsConstructor
@Builder
public class DocumentDto {

    @JsonProperty("document_id")
    private String documentId;

    @JsonProperty("loan_amount")
    private BigDecimal loanAmount;

    @JsonProperty("full_name")
    private String fullName;

    @JsonProperty("id_card_no")
    private String idCardNo;

    @JsonProperty("phone_number")
    private String phoneNumber;

    @JsonProperty("job_name")
    private String jobName;

    @JsonProperty("collateral")
    private String collateral;

    @JsonProperty("loan_reason")
    private String loanReason;

    @JsonProperty("status")
    private DocumentStatus status;

    @JsonProperty("status_changed_by")
    private String statusChangedBy;

    @JsonProperty("reason")
    private String reason;
}
