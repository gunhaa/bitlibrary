package bitcopark.library.controller.intro;

import bitcopark.library.aop.CategoryDTO;
import bitcopark.library.categoryStrategy.CategoryRouter;
import bitcopark.library.categoryStrategy.CategoryStrategy;
import bitcopark.library.categoryStrategy.CategoryStrategyFactory;
import bitcopark.library.entity.Board.Category;
import bitcopark.library.exception.CategoryNotFoundException;
import bitcopark.library.repository.Board.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class IntroController {

    private final CategoryRepository categoryRepository;

    @GetMapping(value = {"/{catLevel1:intro}/{catLevel2}/{catLevel3}", "/{catLevel1:intro}/{catLevel2}/"})
    public String intro(Model model , @ModelAttribute("categoryList") List<CategoryDTO> categoryDTOList
                        ,@PathVariable(name = "catLevel1") String catLevel1
                        ,@PathVariable(name = "catLevel2") String catLevel2
                        ,@PathVariable(name = "catLevel3", required = false) String catLevel3){

        CategoryDTO categoryLevel1 = getCategoryByCategoryEngName(categoryDTOList, catLevel1);
        model.addAttribute("catLevel1", categoryLevel1.getId());

        CategoryDTO categoryLevel2 = getCategoryByCategoryEngName(categoryDTOList, catLevel2);
        model.addAttribute("catLevel2", categoryLevel2.getId());

        CategoryDTO categoryLevel3 = getCategoryByCategoryId(categoryDTOList, catLevel3);
        if(categoryLevel3 != null) {
            model.addAttribute("catLevel3", Integer.parseInt(catLevel3));
        }

        CategoryStrategy strategy = CategoryStrategyFactory.getStrategy(categoryLevel2);
        CategoryRouter router = new CategoryRouter(strategy);

        return router.route(categoryLevel3);
    }

    private static CategoryDTO getCategoryByCategoryEngName(List<CategoryDTO> categoryDTOList, String catLevel) {
        return categoryDTOList.stream()
                .filter(category -> category.getCategoryEngName().equals(catLevel))
                .findFirst().orElseThrow(CategoryNotFoundException::new);
    }

    private static CategoryDTO getCategoryByCategoryId(List<CategoryDTO> categoryDTOList, String catLevel){

        if (catLevel == null) {
            return null;
        }

        return categoryDTOList.stream()
                .filter(categoryDTO -> categoryDTO.getId().intValue() == Integer.parseInt(catLevel))
                .findFirst().orElseThrow(CategoryNotFoundException::new);
    }


}
