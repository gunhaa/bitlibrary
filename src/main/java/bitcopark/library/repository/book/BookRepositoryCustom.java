package bitcopark.library.repository.book;

import bitcopark.library.controller.book.BookSearchDetailCondition;

import java.util.List;

public interface BookRepositoryCustom {

    List<BookSearchDto> findSearchConditionBooks(BookSearchCondition bookSearchCondition);

    List<BookSearchDto> findSearchDetailConditionBooks(BookSearchDetailCondition bookSearchDetailCondition);
}
