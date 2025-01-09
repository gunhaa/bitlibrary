package bitcopark.library.controller.search;

import bitcopark.library.service.Book.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class SearchRestController {

    private final BookService bookService;

//    @GetMapping()
//    public String search(){
//
//        return ;
//    }

}
