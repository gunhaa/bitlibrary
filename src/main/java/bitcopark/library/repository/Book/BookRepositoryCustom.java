package bitcopark.library.repository.Book;

import bitcopark.library.controller.search.BookSearchDetailCondition;

import java.util.List;

public interface BookRepositoryCustom {

    List<BookSearchDto> findSearchConditionBooks(BookSearchCondition bookSearchCondition);

    List<BookSearchDto> findSearchDetailConditionBooks(BookSearchDetailCondition bookSearchDetailCondition);
}
