package bitcopark.library.controller.search;

import bitcopark.library.aop.CategoryDTO;
import bitcopark.library.service.Member.BookRequestPageDto;
import bitcopark.library.service.Member.BookRequestService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
        System.out.println("pageable = " + pageable);
        System.out.println("bookRequestPageDto = " + bookRequestPageDto);


//        // ObjectMapper를 사용하여 JSON 변환
//        ObjectMapper objectMapper = new ObjectMapper();
//        String pageJson = null;
//        try {
//            pageJson = objectMapper.writeValueAsString(bookRequestPageDto);
//        } catch (JsonProcessingException e) {
//            e.printStackTrace();
//        }
//
//        System.out.println("bookRequestPageDto = " + bookRequestPageDto);
//        System.out.println("pageJson = " + pageJson); // JSON 확인을 위한 출력

        return "search/bookRequestBoard";
    }

    @GetMapping("/{catLevel1:search}/{catLevel2:book-req}/apply")
    public String bookReqApply(Model model, @ModelAttribute("categoryDTOList") List<CategoryDTO> categoryDTOList,
                               @PathVariable(name = "catLevel1") String catLevel1,
                               @PathVariable(name = "catLevel2") String catLevel2) {
        setCategoryInModel(model, categoryDTOList, catLevel1, catLevel2);
        return "search/bookRequestForm";
    }

}
