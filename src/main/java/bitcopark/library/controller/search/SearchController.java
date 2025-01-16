package bitcopark.library.controller.search;

import bitcopark.library.aop.CategoryDTO;
import bitcopark.library.categoryStrategy.CategoryRouter;
import bitcopark.library.categoryStrategy.CategoryStrategy;
import bitcopark.library.categoryStrategy.CategoryStrategyFactory;
import bitcopark.library.controller.util.ControllerUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import static bitcopark.library.controller.util.ControllerUtils.*;

@Controller
@RequiredArgsConstructor
public class SearchController {

    @GetMapping(value = {"/{catLevel1:search}/{catLevel2}/{catLevel3}", "/{catLevel1:search}/{catLevel2}"})
    public String search(Model model, @ModelAttribute("categoryDTOList") List<CategoryDTO> categoryDTOList,
                         @PathVariable(name = "catLevel1") String catLevel1,
                         @PathVariable(name = "catLevel2") String catLevel2,
                         @PathVariable(name = "catLevel3", required = false) String catLevel3) {

        CategoryRouterResult categoryRouterResult = setCategoryAndRoute(model, categoryDTOList, catLevel1, catLevel2, catLevel3);

        CategoryRouter router = categoryRouterResult.router();
        CategoryDTO categoryLevel3 = categoryRouterResult.categoryLevel3();

        return router.routing(categoryLevel3);
    }


    @GetMapping("/{catLevel1:search}/{catLevel2:book-req}/list")
    public String bookReqList(Model model, @ModelAttribute("categoryDTOList") List<CategoryDTO> categoryDTOList,
                              @PathVariable(name = "catLevel1") String catLevel1,
                              @PathVariable(name = "catLevel2") String catLevel2,
                              @RequestParam(value = "page", required = false, defaultValue = "1") Integer page) {
        setCategoryInModel(model, categoryDTOList, catLevel1, catLevel2);
        return "search/requestHistory";
    }

    @GetMapping("/{catLevel1:search}/{catLevel2:book-req}/apply")
    public String bookReqApply(Model model, @ModelAttribute("categoryDTOList") List<CategoryDTO> categoryDTOList,
                               @PathVariable(name = "catLevel1") String catLevel1,
                               @PathVariable(name = "catLevel2") String catLevel2) {
        setCategoryInModel(model, categoryDTOList, catLevel1, catLevel2);
        return "search/bookRequestForm";
    }
}
