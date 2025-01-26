package bitcopark.library.repository.book;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

@Data
public class BookStatusDTO {

    private String bookBorrowCount;
    private String bookReservationCount;
    private String bookOverdueCount;

    @QueryProjection
    public BookStatusDTO(String bookBorrowCount, String bookReservationCount, String bookOverdueCount) {
        this.bookBorrowCount = bookBorrowCount;
        this.bookReservationCount = bookReservationCount;
        this.bookOverdueCount = bookOverdueCount;
    }

}
