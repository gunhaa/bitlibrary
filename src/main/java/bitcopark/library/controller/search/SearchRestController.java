package bitcopark.library.controller.search;

import bitcopark.library.repository.Book.BookSearchCondition;
import bitcopark.library.service.Book.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class SearchRestController {

    private final BookService bookService;
    private final BookReservationService bookReservationService;
    private final BookLikeService bookLikeService;

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
}
