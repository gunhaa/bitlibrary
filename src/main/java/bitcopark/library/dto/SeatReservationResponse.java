package bitcopark.library.dto;

import bitcopark.library.entity.librarySeatBooking.SeatReservation;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SeatReservationResponse {

    private Long id;
    private int seatNo;
    private LocalDateTime reservationStart;
    private LocalDateTime reservationEnd;
    private LocalDateTime createdDate;

    public SeatReservationResponse(SeatReservation seatReservation) {
        this.id = seatReservation.getId();
        this.seatNo = seatReservation.getSeatNo();
        this.reservationStart = seatReservation.getReservationStart();
        this.reservationEnd = seatReservation.getReservationEnd();
        this.createdDate = seatReservation.getCreatedDate();
    }
}
