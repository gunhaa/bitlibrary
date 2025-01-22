package bitcopark.library.controller.user;

import bitcopark.library.aop.CategoryDTO;
import bitcopark.library.controller.util.ControllerUtils;
import bitcopark.library.entity.board.Board;
import bitcopark.library.entity.board.Category;
import bitcopark.library.service.Board.BoardService;
import bitcopark.library.service.Board.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

import static bitcopark.library.controller.util.ControllerUtils.setCategoryAndRoute;


@Controller
@RequiredArgsConstructor
public class UserController {

    private final BoardService boardService;
    private final CategoryService categoryService;

    @GetMapping(value="{catLevel1:user}/{catLevel2:faq}")
    public String user(Model model, @ModelAttribute("categoryDTOList") List<CategoryDTO> categoryDTOList
            ,@PathVariable(name = "catLevel1") String catLevel1
            ,@PathVariable(name = "catLevel2") String catLevel2){

        ControllerUtils.CategoryRouterResult categoryRouterResult = setCategoryAndRoute(model, categoryDTOList, catLevel1, catLevel2, null);

        return categoryRouterResult.router().routing(categoryRouterResult.categoryLevel3());
    }

    @GetMapping(value="{catLevel1:user}/{catLevel2:notice|inquiries|book-reviews}")
    public String board(Model model, @ModelAttribute("categoryDTOList") List<CategoryDTO> categoryDTOList
            , @PathVariable(name = "catLevel1") String catLevel1
            , @PathVariable(name = "catLevel2") String catLevel2
            , Pageable pageable){

        setCategoryAndRoute(model, categoryDTOList, catLevel1, catLevel2, null);

        // logic
        Category category = categoryService.getIdByEngName(catLevel2);
        // pagination
        Page<Board> boardPage = boardService.selectBoardList(category.getId(), pageable);

        System.out.println("boardPage = " + boardPage);
        // model addAttribute
        model.addAttribute("boardPage", boardPage);
        model.addAttribute("cateCode", category.getId());
        model.addAttribute("cateName", category.getCategoryName());

        return "user/boardList";
    }
}
