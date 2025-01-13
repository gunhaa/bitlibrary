package bitcopark.library.repository.Book;

import java.util.List;

public interface BookRepositoryCustom {

    List<BookSearchDto> findAllBooks(BookSearchCondition bookSearchCondition);

}
