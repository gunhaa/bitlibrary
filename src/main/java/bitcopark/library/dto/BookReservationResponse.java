package bitcopark.library.dto;

import bitcopark.library.entity.book.BookReservation;
import lombok.Data;

import java.time.LocalDate;

@Data
public class BookReservationResponse {

    private Long id;
    private String title;
    private LocalDate reservationDate;
    private LocalDate reservationEndDate;

    public BookReservationResponse(BookReservation bookReservation) {
        this.id = bookReservation.getId();
        this.title = bookReservation.getBook().getTitle();
        this.reservationDate = bookReservation.getReservationDate();
        this.reservationEndDate = bookReservation.getReservationDate().plusDays(2);
    }
}
