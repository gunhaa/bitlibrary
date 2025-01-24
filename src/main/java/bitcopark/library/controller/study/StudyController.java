package bitcopark.library.controller.study;

import bitcopark.library.aop.CategoryDTO;
import bitcopark.library.categoryStrategy.CategoryRouter;
import bitcopark.library.categoryStrategy.CategoryStrategy;
import bitcopark.library.categoryStrategy.CategoryStrategyFactory;
import bitcopark.library.controller.util.ControllerUtils;
import bitcopark.library.repository.board.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import static bitcopark.library.controller.util.ControllerUtils.*;
import static bitcopark.library.controller.util.ControllerUtils.setCategoryAndRoute;

@Controller
@RequiredArgsConstructor
public class StudyController {


    @GetMapping(value = {"/{catLevel1:study}/{catLevel2}/{catLevel3}", "/{catLevel1:study}/{catLevel2}/"})
    public String study(Model model, @ModelAttribute("categoryDTOList") List<CategoryDTO> categoryDTOList,
                        @PathVariable String catLevel1,
                        @PathVariable String catLevel2,
                        @PathVariable(required = false) String catLevel3,
                        @RequestParam(value = "sub", required = false) String subCategoryEngName) {

        CategoryRouterResult categoryRouterResult = setCategoryAndRoute(model, categoryDTOList, catLevel1, catLevel2, catLevel3);

        CategoryRouter router = categoryRouterResult.router();
        CategoryDTO categoryLevel3 = categoryRouterResult.categoryLevel3();
        categoryLevel3.setSubCategoryEngName(subCategoryEngName);

        return router.routing(categoryLevel3);
    }

}
