package vn.com.tdtu.document.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.com.tdtu.common.aop.FieldNotNull;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class GetDocumentRequest {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    @JsonProperty("from_date")
    @FieldNotNull
    private LocalDate fromDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    @JsonProperty("to_date")
    @FieldNotNull
    private LocalDate toDate;

    @JsonProperty("document_id")
    private String documentId;

    @JsonProperty("page_number")
    private Integer pageNumber;


    @JsonProperty("page_size")
    private Integer pageSize;
}
