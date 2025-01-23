package bitcopark.library.repository.book;

import bitcopark.library.entity.book.BookState;
import bitcopark.library.entity.book.BookSupple;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

import java.time.LocalDate;

@Data
public class BookSearchDto {
    
    private final String bookAuthor;
    private final String bookTitle;
    private final String publisher;
    private final String publicationDate;
    private final String isbn;
    private final String thumbnail;
    private final BookState bookState;
    private final BookSupple bookSupple;

    // book의 예약 정보(횟수) book 번호와 일치하는 것의 예약정보 갯수
    private final Long bookBorrowCount;

    // 반납 예정일 Borrow 테이블의 반납예정일자
    private final LocalDate returnDueDate;

    @QueryProjection
    public BookSearchDto(String bookAuthor, String bookTitle, String publisher, String publicationDate, String isbn, String thumbnail, BookState bookState, BookSupple bookSupple, Long bookBorrowCount, LocalDate returnDueDate) {
        this.bookAuthor = bookAuthor;
        this.bookTitle = bookTitle;
        this.publisher = publisher;
        this.publicationDate = publicationDate;
        this.isbn = isbn;
        this.thumbnail = thumbnail;
        this.bookState = bookState;
        this.bookSupple = bookSupple;
        this.bookBorrowCount = bookBorrowCount;
        this.returnDueDate = returnDueDate;
    }
}
