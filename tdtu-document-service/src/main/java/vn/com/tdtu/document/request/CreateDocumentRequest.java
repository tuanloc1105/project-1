package vn.com.tdtu.document.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.com.tdtu.common.aop.FieldNotNull;

import java.math.BigDecimal;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class CreateDocumentRequest {

    @JsonProperty("loan_amount")
    @FieldNotNull
    private BigDecimal loanAmount;

    @JsonProperty("full_name")
    @FieldNotNull
    private String fullName;

    @JsonProperty("id_card_no")
    @FieldNotNull
    private String idCardNo;

    @JsonProperty("phone_number")
    @FieldNotNull
    private String phoneNumber;

    @JsonProperty("job_name")
    @FieldNotNull
    private String jobName;

    @JsonProperty("collateral")
    @FieldNotNull
    private String collateral;

    @JsonProperty("loan_reason")
    @FieldNotNull
    private String loanReason;
}
