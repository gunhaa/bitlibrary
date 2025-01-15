package bitcopark.library.controller.util;

import bitcopark.library.aop.CategoryDTO;
import bitcopark.library.categoryStrategy.CategoryRouter;
import bitcopark.library.categoryStrategy.CategoryStrategy;
import bitcopark.library.categoryStrategy.CategoryStrategyFactory;
import bitcopark.library.exception.CategoryNotFoundException;
import org.springframework.ui.Model;

import java.util.List;

public class ControllerUtils {

    public static CategoryDTO getCategoryByCategoryEngName(List<CategoryDTO> categoryDTOList, String catLevel) {
        return categoryDTOList.stream()
                .filter(category -> category.getCategoryEngName().equals(catLevel))
                .findFirst().orElseThrow(CategoryNotFoundException::new);
    }

    public static CategoryDTO getCategoryByCategoryId(List<CategoryDTO> categoryDTOList, String catLevel){

        if (catLevel == null) {
            return null;
        }

        return categoryDTOList.stream()
                .filter(categoryDTO -> categoryDTO.getId().intValue() == Integer.parseInt(catLevel))
                .findFirst().orElseThrow(CategoryNotFoundException::new);
    }

    public static void setCategoryInModel(Model model, List<CategoryDTO> categoryDTOList, String catLevel1, String catLevel2){
        CategoryDTO categoryLevel1 = ControllerUtils.getCategoryByCategoryEngName(categoryDTOList, catLevel1);
        model.addAttribute("catLevel1", categoryLevel1.getId());

        CategoryDTO categoryLevel2 = ControllerUtils.getCategoryByCategoryEngName(categoryDTOList, catLevel2);
        model.addAttribute("catLevel2", categoryLevel2.getId());
    }

    public static CategoryRouterResult setCategoryAndRoute(Model model, List<CategoryDTO> categoryDTOList, String catLevel1, String catLevel2, String catLevel3){
        CategoryDTO categoryLevel1 = ControllerUtils.getCategoryByCategoryEngName(categoryDTOList, catLevel1);
        model.addAttribute("catLevel1", categoryLevel1.getId());

        CategoryDTO categoryLevel2 = ControllerUtils.getCategoryByCategoryEngName(categoryDTOList, catLevel2);
        model.addAttribute("catLevel2", categoryLevel2.getId());

        CategoryDTO categoryLevel3 = ControllerUtils.getCategoryByCategoryId(categoryDTOList, catLevel3);
        if(categoryLevel3 != null) {
            model.addAttribute("catLevel3", Integer.parseInt(catLevel3));
        }

        CategoryStrategy strategy = CategoryStrategyFactory.getStrategy(categoryLevel2);
        CategoryRouter router = new CategoryRouter(strategy);
        return new CategoryRouterResult(router, categoryLevel3);
    }

    public record CategoryRouterResult(CategoryRouter router, CategoryDTO categoryLevel3) {
    }

}
