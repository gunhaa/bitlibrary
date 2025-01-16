package bitcopark.library.repository.Member;

import bitcopark.library.service.Member.BookRequestPageDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BookRequestRepositoryCustom {

    Page<BookRequestPageDto> getBookRequestPage(Pageable page);

}
