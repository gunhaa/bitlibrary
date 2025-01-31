package bitcopark.library.dto;

import bitcopark.library.entity.book.BookBorrow;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class BookLoanHistoryResponse {

    private String title;
    private LocalDate borrowDate;
    private LocalDate returnDueDate;
    private LocalDateTime returnDate;

    public BookLoanHistoryResponse(BookBorrow bookBorrow) {
        this.title = bookBorrow.getBook().getTitle();
        this.borrowDate = bookBorrow.getBorrowDate();
        this.returnDueDate = bookBorrow.getReturnDueDate();
        this.returnDate = bookBorrow.getReturnDate();
    }
}
