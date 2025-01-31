package bitcopark.library.controller.study;

import bitcopark.library.dto.BookApplicationResponse;
import bitcopark.library.dto.BookLoanHistoryResponse;
import bitcopark.library.dto.BookLoanResponse;
import bitcopark.library.dto.BookReservationResponse;
import bitcopark.library.jwt.LoginMemberDTO;
import bitcopark.library.service.Book.BookBorrowService;
import bitcopark.library.service.Book.BookLikeService;
import bitcopark.library.service.Book.BookRequestService;
import bitcopark.library.service.Book.BookReservationService;
import bitcopark.library.service.Class.ClassApplicantService;
import bitcopark.library.service.LibrarySeatBooking.SeatReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/study")
@RequiredArgsConstructor
public class StudyRestController {

    private final BookBorrowService bookBorrowService;
    private final BookReservationService bookReservationService;
    private final BookRequestService bookRequestService;
    private final SeatReservationService seatReservationService;
    private final ClassApplicantService classApplicantService;
    private final BookLikeService bookLikeService;

    @GetMapping("/books/loans/current")
    public List<BookLoanResponse> getCurrentlyBorrowedBooks(@RequestAttribute LoginMemberDTO loginMember) {
        return bookBorrowService.getCurrentlyBorrowedBooks(loginMember.getEmail());
    }

    @DeleteMapping("/reservation/book")
    public ResponseEntity<Void> cancelBookReservation(@RequestBody Long id, @RequestAttribute LoginMemberDTO loginMember) {
        bookReservationService.delete(id, loginMember.getEmail());
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/reservation/seat")
    public ResponseEntity<Void> cancelSeatReservation(@RequestBody Long id, @RequestAttribute LoginMemberDTO loginMember) {
        seatReservationService.delete(id, loginMember.getEmail());
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/applicant/class")
    public ResponseEntity<Void> cancelClassApplicant(@RequestBody Long id, @RequestAttribute LoginMemberDTO loginMember) {
        classApplicantService.delete(id, loginMember.getEmail());
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/like/book")
    public ResponseEntity<Void> cancelBookLike(@PathVariable Long id, @RequestAttribute LoginMemberDTO loginMember) {
        bookLikeService.delete(id, loginMember.getEmail());
        return ResponseEntity.noContent().build();
    }
}
