package bitcopark.library.controller.search;

import bitcopark.library.controller.book.BookRequestCondition;
import bitcopark.library.controller.book.BookRequestResponseDto;
import bitcopark.library.controller.book.BookSearchDetailCondition;
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
    public SearchBooklistAndLikelistDTO search(BookSearchCondition bookSearchCondition){
        return bookService.searchBooklistAndLikelist(bookSearchCondition);
    }

    @GetMapping("/{catLevel1:search}/books/detail/v1")
    public SearchBooklistAndLikelistDTO searchDetail(BookSearchDetailCondition bookSearchDetailCondition){
        return bookService.searchDetailBooklistAndLikelist(bookSearchDetailCondition);
    }

    @PostMapping("/{catLevel1:search}/books/reservation/v1")
    public ReservationStatus reservation(@RequestBody ReservationCondition condition){
        return bookReservationService.registerBookReservationWithIsbnAndMemberId(condition);
    }

    @PostMapping("/{catLevel1:search}/books/like/v1")
    public LikeStatus like(@RequestBody LikeCondition condition){
        return bookLikeService.toggleLike(condition);
    }

    @PostMapping("/{catLevel1:search}/book-req/apply/v1")
    public BookRequestResponseDto bookRequestApply(@RequestBody BookRequestCondition applyCondition){
        return bookRequestService.registerBookRequest(applyCondition);
    }
}
