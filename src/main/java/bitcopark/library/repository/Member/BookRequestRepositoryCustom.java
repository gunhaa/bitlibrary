package bitcopark.library.repository.Member;

import bitcopark.library.service.Member.BookRequestListDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BookRequestRepositoryCustom {

    List<BookRequestListDto> getBookRequestPage(Pageable page);

}
