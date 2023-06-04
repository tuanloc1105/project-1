package vn.com.tdtu.document.service;

import vn.com.tdtu.common.object.PageResponse;
import vn.com.tdtu.document.dto.DocumentDto;
import vn.com.tdtu.document.request.ChangeDocumentStatusRequest;
import vn.com.tdtu.document.request.CreateDocumentRequest;
import vn.com.tdtu.document.request.GetDocumentRequest;

public interface DocumentService {
    void createDocument(CreateDocumentRequest request);

    PageResponse<DocumentDto> getDocument(GetDocumentRequest request);

    void changeDocumentStatus(ChangeDocumentStatusRequest request);
}
