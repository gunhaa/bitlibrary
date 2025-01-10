package bitcopark.library.controller.search;

import bitcopark.library.service.Book.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class SearchRestController {

    private final BookService bookService;

    @GetMapping("/{catLevel1:search}/books/v1")
    public String search(Model model, @RequestParam String query, @RequestParam String key){

        System.out.println("Controller get query = " + query + ", key = " + key);
        // repository에서 가져와야 하는것 -> book의 모든 정보, 그 book의 예약 정보(횟수), 반납예정일

        return "common/main";
    }

}
