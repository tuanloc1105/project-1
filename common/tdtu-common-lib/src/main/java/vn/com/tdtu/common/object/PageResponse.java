package vn.com.tdtu.common.object;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Data
public class PageResponse<T> implements Serializable {
    @Serial
    private static final long serialVersionUID = 8325398541544489844L;

    @JsonProperty("page_number")
    private Integer pageNumber;

    @JsonProperty("page_size")
    private Integer pageSize;

    @JsonProperty("total_pages")
    private Integer totalPages;

    @JsonProperty("number_of_elements")
    private Integer numberOfElements;

    @JsonProperty("total_elements")
    private Long totalElements;

    @JsonProperty("first_page")
    private Boolean firstPage;

    @JsonProperty("last_page")
    private Boolean lastPage;

    @JsonProperty("content")
    private List<T> content;

    public PageResponse() {
    }

    @Builder
    public PageResponse(Integer pageNumber, Integer pageSize, Integer totalPages, Integer numberOfElements, Long totalElements, Boolean firstPage, Boolean lastPage, List<T> content) {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.totalPages = totalPages;
        this.numberOfElements = numberOfElements;
        this.totalElements = totalElements;
        this.firstPage = firstPage;
        this.lastPage = lastPage;
        this.content = content;
    }

    public static <T, V> PageResponse<V> create(Page<T> page, Function<List<T>, List<V>> function) {
        return PageResponse
                .<V>builder()
                .pageNumber(page.getNumber())
                .pageSize(page.getSize())
                .totalPages(page.getTotalPages())
                .numberOfElements(page.getNumberOfElements())
                .totalElements(page.getTotalElements())
                .firstPage(page.isFirst())
                .lastPage(page.isLast())
                .content(function.apply(page.getContent()))
                .build();
    }

    public static <T, V> PageResponse<V> create(Page<T> page, Handler<T, V> handler) {
        return PageResponse
                .<V>builder()
                .pageNumber(page.getNumber())
                .pageSize(page.getSize())
                .totalPages(page.getTotalPages())
                .numberOfElements(page.getNumberOfElements())
                .totalElements(page.getTotalElements())
                .firstPage(page.isFirst())
                .lastPage(page.isLast())
                .content(page.getContent().stream().map(handler::handle).collect(Collectors.toList()))
                .build();
    }

    public interface Handler<T, V> {
        V handle(T input);
    }

}
