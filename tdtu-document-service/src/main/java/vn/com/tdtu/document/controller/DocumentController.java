package vn.com.tdtu.document.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.com.tdtu.common.aop.HasRoles;
import vn.com.tdtu.common.object.PageResponse;
import vn.com.tdtu.common.object.RequestType;
import vn.com.tdtu.common.object.ResponseType;
import vn.com.tdtu.document.dto.DocumentDto;
import vn.com.tdtu.document.request.ChangeDocumentStatusRequest;
import vn.com.tdtu.document.request.CreateDocumentRequest;
import vn.com.tdtu.document.request.GetDocumentRequest;
import vn.com.tdtu.document.service.DocumentService;

@RestController
@RequestMapping("/document")
@RequiredArgsConstructor
@CrossOrigin("*")
public class DocumentController {

    private final DocumentService documentService;

    @PostMapping("/create")
    @HasRoles(roles = "CUSTOMER")
    public ResponseType<String> create(@RequestBody RequestType<CreateDocumentRequest> request) {
        this.documentService.createDocument(request.getRequest());
        return ResponseType.ok("OK");
    }

    @PostMapping("/get")
    @HasRoles(roles = {"LSO", "ADMIN"})
    public ResponseType<PageResponse<DocumentDto>> get(@RequestBody RequestType<GetDocumentRequest> request) {
        return ResponseType.ok(this.documentService.getDocument(request.getRequest()));
    }

    @PostMapping("/change")
    @HasRoles(roles = {"LSO"})
    public ResponseType<String> changeStatus(@RequestBody RequestType<ChangeDocumentStatusRequest> request) {
        this.documentService.changeDocumentStatus(request.getRequest());
        return ResponseType.ok("OK");
    }


}
