package bitcopark.library.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

@Data
public class BookStatusDTO {

    private Long bookBorrowCount;
    private Long bookReservationCount;
    private Long bookOverdueCount;

    @QueryProjection
    public BookStatusDTO(Long bookBorrowCount, Long bookReservationCount, Long bookOverdueCount) {
        this.bookBorrowCount = bookBorrowCount;
        this.bookReservationCount = bookReservationCount;
        this.bookOverdueCount = bookOverdueCount;
    }

}
