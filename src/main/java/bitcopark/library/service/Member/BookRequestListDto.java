package bitcopark.library.service.Member;

import bitcopark.library.entity.member.BookRequestApprove;
import lombok.Data;

import java.time.LocalDate;

@Data
public class BookRequestListDto {

    // bookNo 대신 사용
    private String isbn;
    // memberNo는 컨트롤러에서 세션으로 얻어오는 방법으로 차후 업데이트
    private Long memberId;
    private String name;
    private String bookRequestTitle;
    private BookRequestApprove bookRequestApprove;
    private String bookRequestOpinion;
    private LocalDate bookRequestCreatedDate;

}
