package vn.com.tdtu.document.entity.repository;

import org.springframework.stereotype.Repository;
import vn.com.tdtu.common.entity.repository.BaseRepository;
import vn.com.tdtu.document.entity.Document;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface DocumentRepository extends BaseRepository<Document> {

    Long countByCreatedAtBetween(LocalDateTime fromDate, LocalDateTime toDate);

    Optional<Document> findByDocumentIdAndActiveIsTrueAndCreatedAtBetween(String documentId, LocalDateTime start, LocalDateTime end);

}
