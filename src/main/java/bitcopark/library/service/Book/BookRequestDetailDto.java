package bitcopark.library.service.Book;

import bitcopark.library.entity.member.BookRequestApprove;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class BookRequestDetailDto {

    private BookRequestApprove bookRequestApprove;
    private LocalDate bookRequestCreatedDate;
    private String name;
    private String bookTitle;
    private String bookAuthor;
    private String bookPublisher;
    private LocalDate bookPublicationDate;
    private String opinion;
    // memberNo는 컨트롤러에서 세션으로 얻어오는 방법으로 차후 업데이트
    private Long memberId;

    @QueryProjection
    public BookRequestDetailDto(BookRequestApprove bookRequestApprove, LocalDateTime bookRequestCreatedDate, String name, String bookTitle, String bookAuthor, String bookPublisher, LocalDate bookPublicationDate, String opinion) {
        this.bookRequestApprove = bookRequestApprove;
        this.bookRequestCreatedDate = bookRequestCreatedDate.toLocalDate();
        this.name = name;
        this.bookTitle = bookTitle;
        this.bookAuthor = bookAuthor;
        this.bookPublisher = bookPublisher;
        this.bookPublicationDate = bookPublicationDate;
        this.opinion = opinion;
    }
}
