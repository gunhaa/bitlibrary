package bitcopark.library.repository.Member;

import bitcopark.library.service.Member.BookRequestPageDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BookRequestRepositoryCustom {

    List<BookRequestPageDto> getBookRequestPage(Pageable page);

}
