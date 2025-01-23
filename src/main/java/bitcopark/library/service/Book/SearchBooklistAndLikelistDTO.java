package bitcopark.library.service.Book;

import bitcopark.library.repository.book.BookLikeDto;
import bitcopark.library.repository.book.BookSearchDto;
import lombok.Data;

import java.util.List;

@Data
public class SearchBooklistAndLikelistDTO {
    private final List<BookSearchDto> bookList;
    private final List<BookLikeDto> likeList;
}
