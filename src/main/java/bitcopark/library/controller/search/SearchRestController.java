package bitcopark.library.controller.search;

import bitcopark.library.controller.book.BookRequestCondition;
import bitcopark.library.controller.book.BookRequestResponseDto;
import bitcopark.library.controller.book.BookSearchDetailCondition;
import bitcopark.library.jwt.LoginMemberDTO;
import bitcopark.library.repository.book.BookRequestRepository;
import bitcopark.library.repository.book.BookSearchCondition;
import bitcopark.library.service.Book.*;
import bitcopark.library.service.Book.BookRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class SearchRestController {

    private final BookService bookService;
    private final BookReservationService bookReservationService;
    private final BookLikeService bookLikeService;
    private final BookRequestService bookRequestService;

    @GetMapping("/{catLevel1:search}/books/v1")
    public SearchBooklistAndLikelistDTO search(BookSearchCondition bookSearchCondition,
                                               @RequestAttribute(value = "loginMember", required = false) LoginMemberDTO loginMember){

        if(loginMember == null){
            return bookService.searchBooklistAndLikelist(bookSearchCondition);
        }
        return bookService.searchBooklistAndLikelist(bookSearchCondition, loginMember);
    }

    @GetMapping("/{catLevel1:search}/books/detail/v1")
    public SearchBooklistAndLikelistDTO searchDetail(BookSearchDetailCondition bookSearchDetailCondition,
                                                     @RequestAttribute(value = "loginMember", required = false) LoginMemberDTO loginMember){
        if(loginMember == null){
            return bookService.searchDetailBooklistAndLikelist(bookSearchDetailCondition);
        }
        return bookService.searchDetailBooklistAndLikelist(bookSearchDetailCondition, loginMember);
    }

    @PostMapping("/{catLevel1:search}/books/reservation/v1")
    public ReservationStatus reservation(@RequestBody ReservationCondition condition){
        return bookReservationService.registerBookReservationWithIsbnAndMemberId(condition);
    }

    @PostMapping("/{catLevel1:search}/books/like/v1")
    public LikeStatus like(@RequestBody LikeCondition condition, @RequestAttribute("loginMember") LoginMemberDTO loginMember){
        condition.setEmail(loginMember.getEmail());
        return bookLikeService.toggleLike(condition);
    }

}
