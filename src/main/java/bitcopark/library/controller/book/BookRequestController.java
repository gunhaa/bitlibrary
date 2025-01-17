package bitcopark.library.controller.book;

import bitcopark.library.aop.CategoryDTO;
import bitcopark.library.service.Book.BookRequestDetailDto;
import bitcopark.library.service.Book.BookRequestPageDto;
import bitcopark.library.service.Book.BookRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

import static bitcopark.library.controller.util.ControllerUtils.setCategoryInModel;

@Controller
@RequiredArgsConstructor
public class BookRequestController {

    private final BookRequestService bookRequestService;

    @GetMapping("/{catLevel1:search}/{catLevel2:book-req}/list")
    public String bookRequestBoard(Model model, @ModelAttribute("categoryDTOList") List<CategoryDTO> categoryDTOList,
                                   @PathVariable(name = "catLevel1") String catLevel1,
                                   @PathVariable(name = "catLevel2") String catLevel2,
                                   @PageableDefault(page = 0, size = 10) Pageable pageable) {
        setCategoryInModel(model, categoryDTOList, catLevel1, catLevel2);
        Page<BookRequestPageDto> bookRequestPageDto = bookRequestService.getBookRequestPage(pageable);
        // Session에서 memberId얻어와서 DTO 객체에 추가해야함
        model.addAttribute("page", bookRequestPageDto);

        return "search/bookRequestBoard";
    }

    @GetMapping("/{catLevel1:search}/{catLevel2:book-req}/apply")
    public String bookReqApply(Model model, @ModelAttribute("categoryDTOList") List<CategoryDTO> categoryDTOList,
                               @PathVariable(name = "catLevel1") String catLevel1,
                               @PathVariable(name = "catLevel2") String catLevel2) {
        setCategoryInModel(model, categoryDTOList, catLevel1, catLevel2);
        return "search/bookRequestForm";
    }

    @GetMapping("/{catLevel1:search}/{catLevel2:book-req}/list/{isbn}")
    public String bookRequestBoardDetail(Model model, @ModelAttribute("categoryDTOList") List<CategoryDTO> categoryDTOList,
                                         @PathVariable(name = "catLevel1") String catLevel1,
                                         @PathVariable(name = "catLevel2") String catLevel2,
                                         @PathVariable String isbn) {
        setCategoryInModel(model, categoryDTOList, catLevel1, catLevel2);
        BookRequestDetailDto bookRequestDetailDto = bookRequestService.getBookRequestDetailsByIsbn(isbn);
        System.out.println("isbn = " + isbn);
        System.out.println("bookRequestDetailDto = " + bookRequestDetailDto);
        model.addAttribute("bookRequestDetail" , bookRequestDetailDto);
        return "search/bookRequestDetail";
    }
}
