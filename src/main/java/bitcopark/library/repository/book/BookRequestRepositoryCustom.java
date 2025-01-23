package bitcopark.library.repository.book;

import bitcopark.library.service.Book.BookRequestDetailDto;
import bitcopark.library.service.Book.BookRequestPageDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookRequestRepositoryCustom {

    Page<BookRequestPageDto> getBookRequestPage(Pageable page);

    BookRequestDetailDto getBookRequestDetail(String isbn);
}
