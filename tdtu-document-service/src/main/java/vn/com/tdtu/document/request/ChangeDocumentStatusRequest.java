package vn.com.tdtu.document.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.com.tdtu.document.enums.DocumentStatus;

@Data
@NoArgsConstructor
public class ChangeDocumentStatusRequest {
    @JsonProperty("document_id")
    private String documentId;

    @JsonProperty("status")
    private DocumentStatus status;
}
