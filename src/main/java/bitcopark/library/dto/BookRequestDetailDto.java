package bitcopark.library.dto;

import bitcopark.library.entity.member.BookRequestApprove;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class BookRequestDetailDto {

    private BookRequestApprove bookRequestApprove;
    private LocalDate bookRequestCreatedDate;
    private String email;
    private String bookTitle;
    private String bookAuthor;
    private String bookPublisher;
    private LocalDate bookPublicationDate;
    private String opinion;
    private String loginMemberEmail;
    private String isbn;

    @QueryProjection
    public BookRequestDetailDto(BookRequestApprove bookRequestApprove, LocalDateTime bookRequestCreatedDate, String email, String bookTitle, String bookAuthor, String bookPublisher, LocalDate bookPublicationDate, String opinion, String isbn) {
        this.bookRequestApprove = bookRequestApprove;
        this.bookRequestCreatedDate = bookRequestCreatedDate.toLocalDate();
        this.email = email;
        this.bookTitle = bookTitle;
        this.bookAuthor = bookAuthor;
        this.bookPublisher = bookPublisher;
        this.bookPublicationDate = bookPublicationDate;
        this.opinion = opinion;
        this.isbn = isbn;
    }
}
