package bitcopark.library.dto;

import bitcopark.library.entity.librarySeatBooking.SeatReservation;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RoomReservationResponse {

    private LocalDateTime reservationStart;
    private LocalDateTime reservationEnd;
    private LocalDateTime createdDate;

    public RoomReservationResponse(SeatReservation seatReservation) {
        this.reservationStart = seatReservation.getReservationStart();
        this.reservationEnd = seatReservation.getReservationEnd();
        this.createdDate = seatReservation.getCreatedDate();
    }
}
