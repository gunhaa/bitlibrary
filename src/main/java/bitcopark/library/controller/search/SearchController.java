package bitcopark.library.controller.search;

import bitcopark.library.aop.CategoryDTO;
import bitcopark.library.categoryStrategy.CategoryRouter;
import bitcopark.library.dto.LoginMemberDTO;
import bitcopark.library.repository.book.BookSearchCondition;
import bitcopark.library.service.book.BookService;
import bitcopark.library.dto.SearchBooklistAndLikelistDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static bitcopark.library.controller.util.ControllerUtils.*;

@Controller
@RequiredArgsConstructor
public class SearchController {

    private final BookService bookService;

    @GetMapping(value = {"/{catLevel1:search}/{catLevel2}/{catLevel3}", "/{catLevel1:search}/{catLevel2}"})
    public String search(Model model, @ModelAttribute("categoryDTOList") List<CategoryDTO> categoryDTOList,
                         @PathVariable(name = "catLevel1") String catLevel1,
                         @PathVariable(name = "catLevel2") String catLevel2,
                         @PathVariable(name = "catLevel3", required = false) String catLevel3,
                         @RequestParam(required = false) BookSearchCondition bookSearchCondition,
                         @RequestAttribute(value = "loginMember", required = false) LoginMemberDTO loginMember) {

        CategoryRouterResult categoryRouterResult = setCategoryAndRoute(model, categoryDTOList, catLevel1, catLevel2, catLevel3);

        CategoryRouter router = categoryRouterResult.router();
        CategoryDTO categoryLevel3 = categoryRouterResult.categoryLevel3();

        if(bookSearchCondition != null){
            if(loginMember == null){
                SearchBooklistAndLikelistDTO searchBookList = bookService.searchBooklistAndLikelist(bookSearchCondition);
                model.addAttribute("searchBookList", searchBookList);
            } else {
                SearchBooklistAndLikelistDTO searchBookList = bookService.searchBooklistAndLikelist(bookSearchCondition, loginMember);
                model.addAttribute("searchBookList", searchBookList);
            }
        }
        return router.routing(categoryLevel3);
    }

}
