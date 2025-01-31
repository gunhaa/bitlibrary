package bitcopark.library.dto;

import bitcopark.library.entity.book.BookBorrow;
import lombok.Data;

import java.time.LocalDate;

@Data
public class BookLoanResponse {

    private String title;
    private LocalDate borrowDate;
    private LocalDate returnDueDate;

    public BookLoanResponse(BookBorrow bookBorrow) {
        this.title = bookBorrow.getBook().getTitle();
        this.borrowDate = bookBorrow.getBorrowDate();
        this.returnDueDate = bookBorrow.getReturnDueDate();
    }
}
