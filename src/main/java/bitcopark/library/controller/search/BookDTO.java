package bitcopark.library.controller.search;

import bitcopark.library.entity.Book.BookState;
import bitcopark.library.entity.Book.BookSupple;
import lombok.Data;

import java.time.LocalDate;

@Data
public class BookDTO {

    private String author;
    private String title;
    private String publisher;
    private String publicationDate;
    private String isbn;
    private String thumbnail;
    private BookState bookState;
    private BookSupple bookSupple;
    private int bookBorrowCount;
    private LocalDate returnDate;

}
