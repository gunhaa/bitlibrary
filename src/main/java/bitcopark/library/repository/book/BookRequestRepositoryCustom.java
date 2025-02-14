package bitcopark.library.repository.book;

import bitcopark.library.dto.BookRequestDetailDto;
import bitcopark.library.dto.BookRequestPageDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookRequestRepositoryCustom {

    Page<BookRequestPageDto> getBookRequestPage(Pageable page);

    BookRequestDetailDto getBookRequestDetail(String isbn);
}
