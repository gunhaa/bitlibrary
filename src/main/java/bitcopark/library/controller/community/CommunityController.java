package bitcopark.library.controller.community;

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
public class CommunityController {

    private final BoardService boardService;
    private final CategoryService categoryService;

    @GetMapping(value="{catLevel1:community}/{catLevel2:reading-room|seminar-room}")
    public String community(Model model, @ModelAttribute("categoryDTOList") List<CategoryDTO> categoryDTOList
            ,@PathVariable(name = "catLevel1") String catLevel1
            ,@PathVariable(name = "catLevel2") String catLevel2) {

        ControllerUtils.CategoryRouterResult categoryRouterResult = setCategoryAndRoute(model, categoryDTOList, catLevel1, catLevel2, null);

        return categoryRouterResult.router().routing(categoryRouterResult.categoryLevel3());
    }

    @GetMapping(value="{catLevel1:community}/{catLevel2:edu-culture-program}")
    public String eduCommunity(Model model, @ModelAttribute("categoryDTOList") List<CategoryDTO> catagoryDTOList
            , @PathVariable(name = "catLevel1") String catLevel1
            , @PathVariable(name = "catLevel2") String catLevel2
            , Pageable pageable) {

        setCategoryAndRoute(model, catagoryDTOList, catLevel1, catLevel2, null);

        Category category = categoryService.getCategoryEngName(catLevel2);

        Page<Board> boardPage = boardService.selectBoardList(category.getId(), pageable);

        model.addAttribute("boardPage", boardPage);

        return "community/edu-culture-program";
    }
}
