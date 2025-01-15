package bitcopark.library.controller.study;

import bitcopark.library.aop.CategoryDTO;
import bitcopark.library.categoryStrategy.CategoryRouter;
import bitcopark.library.categoryStrategy.CategoryStrategy;
import bitcopark.library.categoryStrategy.CategoryStrategyFactory;
import bitcopark.library.controller.util.ControllerUtils;
import bitcopark.library.repository.Board.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class StudyController {

    private final CategoryRepository categoryRepository;

    @GetMapping(value = {"/{catLevel1:study}/{catLevel2}/{catLevel3}", "/{catLevel1:study}/{catLevel2}/"})
    public String study(Model model, @ModelAttribute("categoryDTOList") List<CategoryDTO> categoryDTOList,
                        @PathVariable(name = "catLevel1") String catLevel1,
                        @PathVariable(name = "catLevel2") String catLevel2,
                        @PathVariable(name = "catLevel3", required = false) String catLevel3,
                        @RequestParam(value = "sub", required = false) String subCategoryEngName) {

        CategoryDTO categoryLevel1 = ControllerUtils.getCategoryByCategoryEngName(categoryDTOList, catLevel1);
        model.addAttribute("catLevel1", categoryLevel1.getId());

        CategoryDTO categoryLevel2 = ControllerUtils.getCategoryByCategoryEngName(categoryDTOList, catLevel2);
        model.addAttribute("catLevel2", categoryLevel2.getId());

        CategoryDTO categoryLevel3 = ControllerUtils.getCategoryByCategoryId(categoryDTOList, catLevel3);
        if (categoryLevel3 != null) {
            model.addAttribute("catLevel3", Integer.parseInt(catLevel3));
        }

        CategoryStrategy strategy = CategoryStrategyFactory.getStrategy(categoryLevel2);
        CategoryRouter router = new CategoryRouter(strategy);

        categoryLevel3.setSubCategoryEngName(subCategoryEngName);

        return router.routing(categoryLevel3);
    }
}
