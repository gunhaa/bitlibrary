package bitcopark.library.service.Member;

import bitcopark.library.entity.member.BookRequestApprove;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class BookRequestPageDto {

    // bookNo 대신 사용
    private String isbn;
    // memberNo는 컨트롤러에서 세션으로 얻어오는 방법으로 차후 업데이트
    private Long memberId;
    private String name;
    private String bookTitle;
    private BookRequestApprove bookRequestApprove;
    private String bookRequestOpinion;
    private String bookRequestCreatedDate;

    @QueryProjection
    public BookRequestPageDto(String isbn, String name, String bookTitle, BookRequestApprove bookRequestApprove, String bookRequestOpinion, LocalDateTime bookRequestCreatedDate) {
        this.isbn = isbn;
        // isbn hashing 알고리즘 추가 예정?
        this.name = name;
        this.bookTitle = bookTitle;
        this.bookRequestApprove = bookRequestApprove;
        this.bookRequestOpinion = bookRequestOpinion;
        this.bookRequestCreatedDate = bookRequestCreatedDate.toLocalDate().toString();
    }

}
