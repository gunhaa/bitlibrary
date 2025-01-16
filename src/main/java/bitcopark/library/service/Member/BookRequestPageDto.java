package bitcopark.library.service.Member;

import bitcopark.library.entity.member.BookRequestApprove;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

import java.time.LocalDate;

@Data
public class BookRequestPageDto {

    // bookNo 대신 사용
    private String isbn;
    // memberNo는 컨트롤러에서 세션으로 얻어오는 방법으로 차후 업데이트
    private Long memberId;
    private String name;
    private String bookRequestTitle;
    private BookRequestApprove bookRequestApprove;
    private String bookRequestOpinion;
    private LocalDate bookRequestCreatedDate;

    @QueryProjection
    public BookRequestPageDto(String isbn, String name, String bookRequestTitle, BookRequestApprove bookRequestApprove, String bookRequestOpinion, LocalDate bookRequestCreatedDate) {
        this.isbn = isbn;
        this.name = name;
        this.bookRequestTitle = bookRequestTitle;
        this.bookRequestApprove = bookRequestApprove;
        this.bookRequestOpinion = bookRequestOpinion;
        this.bookRequestCreatedDate = bookRequestCreatedDate;
    }

}
