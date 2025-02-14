package bitcopark.library.repository.book;

import bitcopark.library.dto.BookSearchDetailCondition;
import bitcopark.library.dto.BookSearchDto;

import java.util.List;

public interface BookRepositoryCustom {

    List<BookSearchDto> findSearchConditionBooks(BookSearchCondition bookSearchCondition);

    List<BookSearchDto> findSearchDetailConditionBooks(BookSearchDetailCondition bookSearchDetailCondition);
}
