package bitcopark.library.repository.Book;

import bitcopark.library.service.Book.BookRequestPageDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookRequestRepositoryCustom {

    Page<BookRequestPageDto> getBookRequestPage(Pageable page);

}
