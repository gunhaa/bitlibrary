package bitcopark.library.controller.search;

import bitcopark.library.repository.Book.BookRepository;
import bitcopark.library.repository.Book.BookSearchCondition;
import bitcopark.library.repository.Book.BookSearchDto;
import bitcopark.library.service.Book.BookService;
import bitcopark.library.service.Book.BooklistAndLikelistDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class SearchRestController {

    private final BookService bookService;
    private final BookRepository bookRepository;

    @GetMapping("/{catLevel1:search}/books/v1")
    public BooklistAndLikelistDTO search(Model model, BookSearchCondition bookSearchCondition){

        System.out.println("Controller get query = " + bookSearchCondition.getQuery() + ", key = " + bookSearchCondition.getKey());
        // repository에서 가져와야 하는것 -> book의 모든 정보, 그 book의 예약 정보(횟수), 반납예정일

        return bookService.searchBooklistAndLikelist(bookSearchCondition);
    }
}
