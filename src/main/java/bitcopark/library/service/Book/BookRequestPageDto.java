package bitcopark.library.service.Book;

import bitcopark.library.entity.member.BookRequestApprove;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BookRequestPageDto {

    // bookNo 대신 사용
    private String isbn;
    private String email;
    private String bookTitle;
    private BookRequestApprove bookRequestApprove;
    private String bookRequestCreatedDate;

    @QueryProjection
    public BookRequestPageDto(String isbn, String email, String bookTitle, BookRequestApprove bookRequestApprove, LocalDateTime bookRequestCreatedDate) {
        this.isbn = isbn;
        this.email = email;
        this.bookTitle = bookTitle;
        this.bookRequestApprove = bookRequestApprove;
        this.bookRequestCreatedDate = bookRequestCreatedDate.toLocalDate().toString();
    }

}
