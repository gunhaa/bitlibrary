package bitcopark.library.controller.study;

import bitcopark.library.dto.*;
import bitcopark.library.jwt.LoginMemberDTO;
import bitcopark.library.service.Board.BoardService;
import bitcopark.library.service.Board.ReplyService;
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
    private final BoardService boardService;
    private final ReplyService replyService;

    @GetMapping("/books/loans/current")
    public List<BookLoanResponse> getCurrentlyBorrowedBooks(@RequestAttribute LoginMemberDTO loginMember) {
        return bookBorrowService.getCurrentlyBorrowedBooks(loginMember.getEmail());
    }

    @GetMapping("/books/loans/history")
    public Page<BookLoanHistoryResponse> getBookLoanHistory(@RequestAttribute LoginMemberDTO loginMember, Pageable pageable) {
        return bookBorrowService.getBookLoanHistory(loginMember.getEmail(), pageable);
    }

    @GetMapping("/books/reservation")
    public List<BookReservationResponse> getBookReservations(@RequestAttribute LoginMemberDTO loginMember) {
        return bookReservationService.getBookReservations(loginMember.getEmail());
    }

    @GetMapping("/books/application")
    public Page<BookApplicationResponse> getBookApplications(@RequestAttribute LoginMemberDTO loginMember, Pageable pageable) {
        return bookRequestService.getBookApplications(loginMember.getEmail(), pageable);
    }

    @GetMapping("/seats/reservation")
    public List<SeatReservationResponse> getSeatReservations(@RequestAttribute LoginMemberDTO loginMember) {
        return seatReservationService.getSeatReservations(loginMember.getEmail());
    }

    @GetMapping("/rooms/reservation")
    public List<RoomReservationResponse> getRoomReservations(@RequestAttribute LoginMemberDTO loginMember) {
        return seatReservationService.getRoomReservations(loginMember.getEmail());
    }

    @GetMapping("/classes/applicant")
    public List<ClassApplicantResponse> getClassApplicants(@RequestAttribute LoginMemberDTO loginMember) {
        return classApplicantService.getClassApplicants(loginMember.getEmail());
    }

    @GetMapping("/boards")
    public Page<MyBoardResponse> getMyBoards(@RequestAttribute LoginMemberDTO loginMember, Pageable pageable) {
        return boardService.getMyBoards(loginMember.getEmail(), pageable);
    }

    @GetMapping("/replies")
    public Page<MyReplyResponse> getMyReplies(@RequestAttribute LoginMemberDTO loginMember, Pageable pageable) {
        return replyService.getMyReplies(loginMember.getEmail(), pageable);
    }

    @DeleteMapping("/book/reservation")
    public ResponseEntity<Void> cancelBookReservation(@RequestBody Long id, @RequestAttribute LoginMemberDTO loginMember) {
        bookReservationService.delete(id, loginMember.getEmail());
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/seat/reservation")
    public ResponseEntity<Void> cancelSeatReservation(@RequestBody Long id, @RequestAttribute LoginMemberDTO loginMember) {
        seatReservationService.delete(id, loginMember.getEmail());
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/room/reservation")
    public ResponseEntity<Void> cancelRoomReservation(@RequestBody Long id, @RequestAttribute LoginMemberDTO loginMember) {
        seatReservationService.delete(id, loginMember.getEmail());
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/class/applicant")
    public ResponseEntity<Void> cancelClassApplicant(@RequestBody Long id, @RequestAttribute LoginMemberDTO loginMember) {
        classApplicantService.delete(id, loginMember.getEmail());
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/book/like")
    public ResponseEntity<Void> cancelBookLike(@PathVariable Long id, @RequestAttribute LoginMemberDTO loginMember) {
        bookLikeService.delete(id, loginMember.getEmail());
        return ResponseEntity.noContent().build();
    }
}
