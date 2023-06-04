package vn.com.tdtu.document.service.impl;

import jakarta.persistence.criteria.Predicate;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.com.tdtu.common.exceptions.ErrorCodeEnum;
import vn.com.tdtu.common.exceptions.InternalServiceException;
import vn.com.tdtu.common.object.PageResponse;
import vn.com.tdtu.common.pagable.PageLink;
import vn.com.tdtu.common.service.BaseService;
import vn.com.tdtu.common.service.CurrentUserService;
import vn.com.tdtu.common.utils.DateTimeUtil;
import vn.com.tdtu.common.utils.StringUtil;
import vn.com.tdtu.document.dto.DocumentDto;
import vn.com.tdtu.document.entity.Document;
import vn.com.tdtu.document.entity.repository.DocumentRepository;
import vn.com.tdtu.document.enums.DocumentStatus;
import vn.com.tdtu.document.request.ChangeDocumentStatusRequest;
import vn.com.tdtu.document.request.CreateDocumentRequest;
import vn.com.tdtu.document.request.GetDocumentRequest;
import vn.com.tdtu.document.service.DocumentService;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(rollbackFor = {Exception.class, Throwable.class, RuntimeException.class})
public class DocumentServiceImpl extends BaseService<Document, DocumentRepository> implements DocumentService {

    private final static String DOCUMENT_ID_PREFIX = "DOCUMENT";

    private final ModelMapper modelMapper;
    private final CurrentUserService currentUserService;

    @Autowired
    public DocumentServiceImpl(DocumentRepository repository,
                               ModelMapper modelMapper,
                               CurrentUserService currentUserService) {
        super(repository);
        this.modelMapper = modelMapper;
        this.currentUserService = currentUserService;
    }

    @Override
    public void createDocument(CreateDocumentRequest request) {
        LocalDate currentDate = DateTimeUtil.generateCurrentLocalDateDefault();
        long numberOfDocument = this.repository.countByCreatedAtBetween(
                currentDate.atTime(LocalTime.MIN),
                currentDate.atTime(LocalTime.MAX)
        );
        if (numberOfDocument > 999) {
            throw new InternalServiceException(ErrorCodeEnum.FAILURE.getCode(), "Max document create in a day");
        }
        String username = this.currentUserService.getUsername();
        Document document = this.modelMapper.map(request, Document.class);
        document.setStatus(DocumentStatus.CREATED);
        document.setCreatedBy(username);
        document.setDocumentId(StringUtil.addZeroLeadingNumber(numberOfDocument == 0 ? numberOfDocument + 1 : numberOfDocument, DOCUMENT_ID_PREFIX));
        document.setCreatedAt(DateTimeUtil.generateCurrentTimeDefault());
        this.save(document);
    }

    @Override
    public PageResponse<DocumentDto> getDocument(GetDocumentRequest request) {
        Specification<Document> specification = Specification.where(
                (root, query, criteriaBuilder) -> {
                    List<Predicate> predicates = new ArrayList<>();
                    if (StringUtil.isNotEmpty(request.getDocumentId())) {
                        predicates.add(criteriaBuilder.equal(root.get("documentId"), request.getDocumentId()));
                    } else {
                        predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("createdAt"), request.getToDate().atTime(LocalTime.MIN)));
                        predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("createdAt"), request.getToDate().atTime(LocalTime.MAX)));
                    }
                    return criteriaBuilder.and(predicates.toArray(Predicate[]::new));
                }
        );
        PageLink pageLink = new PageLink(request.getPageSize(), request.getPageNumber());
        Page<Document> page = this.repository.findAll(specification, pageLink.toPageable());
        return PageResponse.create(
                page,
                (PageResponse.Handler<Document, DocumentDto>) input -> modelMapper.map(input, DocumentDto.class)
        );
    }

    @Override
    public void changeDocumentStatus(ChangeDocumentStatusRequest request) {
        LocalDate currentDate = DateTimeUtil.generateCurrentLocalDateDefault();
        Document document = this.repository
                .findByDocumentIdAndActiveIsTrueAndCreatedAtBetween(
                        request.getDocumentId(),
                        currentDate.atTime(LocalTime.MIN),
                        currentDate.atTime(LocalTime.MAX)
                ).orElseThrow(() -> new InternalServiceException(ErrorCodeEnum.NOT_EXISTS.getCode(), "Document does not exist"));
        document.setStatus(request.getStatus());
        document.setUpdatedBy(this.currentUserService.getUsername());
        document.setStatusChangedBy(this.currentUserService.getUsername());
        document.setUpdatedAt(DateTimeUtil.generateCurrentTimeDefault());
        this.repository.save(document);
    }
}
