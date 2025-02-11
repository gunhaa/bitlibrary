package bitcopark.library.controller.study;

import bitcopark.library.dto.*;
import bitcopark.library.jwt.LoginMemberDTO;
import bitcopark.library.oauth2.CustomOAuth2User;
import bitcopark.library.service.Board.BoardService;
import bitcopark.library.service.Board.ReplyService;
import bitcopark.library.service.Book.BookBorrowService;
import bitcopark.library.service.Book.BookLikeService;
import bitcopark.library.service.Book.BookRequestService;
import bitcopark.library.service.Book.BookReservationService;
import bitcopark.library.service.Class.ClassApplicantService;
import bitcopark.library.service.LibrarySeatBooking.SeatReservationService;
import bitcopark.library.service.Member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    private final MemberService memberService;

    @GetMapping("/books/loans/current")
    public List<BookLoanResponse> getCurrentlyBorrowedBooks(@AuthenticationPrincipal CustomOAuth2User user) {
        return bookBorrowService.getCurrentlyBorrowedBooks(user.getName());
    }

    @GetMapping("/books/loans/history")
    public Page<BookLoanHistoryResponse> getBookLoanHistory(@AuthenticationPrincipal CustomOAuth2User user,
                                                            Pageable pageable) {
        return bookBorrowService.getBookLoanHistory(user.getName(), pageable);
    }

    @GetMapping("/books/reservation")
    public List<BookReservationResponse> getBookReservations(@AuthenticationPrincipal CustomOAuth2User user) {
        return bookReservationService.getBookReservations(user.getName());
    }

    @GetMapping("/books/application")
    public Page<BookApplicationResponse> getBookApplications(@AuthenticationPrincipal CustomOAuth2User user,
                                                             Pageable pageable) {
        return bookRequestService.getBookApplications(user.getName(), pageable);
    }

    @DeleteMapping("/book/reservation")
    public ResponseEntity<Void> cancelBookReservation(@AuthenticationPrincipal CustomOAuth2User user,
                                                      @RequestBody Long id) {
        bookReservationService.delete(id, user.getName());
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/book/like")
    public ResponseEntity<Void> cancelBookLike(@AuthenticationPrincipal CustomOAuth2User user,
                                               @RequestBody Long id) {
        bookLikeService.delete(id, user.getName());
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/seats/reservation")
    public List<SeatReservationResponse> getSeatReservations(@AuthenticationPrincipal CustomOAuth2User user) {
        return seatReservationService.getSeatReservations(user.getName());
    }

    @DeleteMapping("/seat/reservation")
    public ResponseEntity<Void> cancelSeatReservation(@AuthenticationPrincipal CustomOAuth2User user,
                                                      @RequestBody Long id) {
        seatReservationService.delete(id, user.getName());
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/rooms/reservation")
    public List<RoomReservationResponse> getRoomReservations(@AuthenticationPrincipal CustomOAuth2User user) {
        return seatReservationService.getRoomReservations(user.getName());
    }

    @DeleteMapping("/room/reservation")
    public ResponseEntity<Void> cancelRoomReservation(@AuthenticationPrincipal CustomOAuth2User user,
                                                      @RequestBody Long id) {
        seatReservationService.delete(id, user.getName());
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/classes/applicant")
    public List<ClassApplicantResponse> getClassApplicants(@AuthenticationPrincipal CustomOAuth2User user) {
        return classApplicantService.getClassApplicants(user.getName());
    }

    @DeleteMapping("/class/applicant")
    public ResponseEntity<Void> cancelClassApplicant(@AuthenticationPrincipal CustomOAuth2User user,
                                                     @RequestBody Long id) {
        classApplicantService.delete(id, user.getName());
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/boards")
    public Page<MyBoardResponse> getMyBoards(@AuthenticationPrincipal CustomOAuth2User user,
                                             Pageable pageable) {
        return boardService.getMyBoards(user.getName(), pageable);
    }

    @GetMapping("/replies")
    public Page<MyReplyResponse> getMyReplies(@AuthenticationPrincipal CustomOAuth2User user,
                                              Pageable pageable) {
        return replyService.getMyReplies(user.getName(), pageable);
    }
}
