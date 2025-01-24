package bitcopark.library.controller.study;

import bitcopark.library.service.Book.BookLikeService;
import bitcopark.library.service.Book.BookReservationService;
import bitcopark.library.service.Class.ClassApplicantService;
import bitcopark.library.service.LibrarySeatBooking.SeatReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class StudyRestController {

    private final BookReservationService bookReservationService;

    @DeleteMapping("/reservation/book/{id}")
    public ResponseEntity<Void> cancelBookReservation(@PathVariable Long id, Principal principal) {
        bookReservationService.delete(id, principal.getName());
        return ResponseEntity.ok().build();
    }
}
