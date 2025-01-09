package bitcopark.library.repository.Book;

import bitcopark.library.controller.search.BookDTO;

import java.util.List;

public interface BookRepositoryCustom {

    List<BookDTO> findAllBooks();

}
