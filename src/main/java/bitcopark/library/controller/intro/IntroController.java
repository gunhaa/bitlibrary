package bitcopark.library.controller.intro;

import bitcopark.library.aop.CategoryDTO;
import bitcopark.library.categoryStrategy.CategoryRouter;
import bitcopark.library.categoryStrategy.CategoryStrategy;
import bitcopark.library.categoryStrategy.CategoryStrategyFactory;
import bitcopark.library.controller.util.ControllerUtils;
import bitcopark.library.controller.util.ControllerUtils.CategoryRouterResult;
import bitcopark.library.repository.Board.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

import static bitcopark.library.controller.util.ControllerUtils.setCategoryAndRoute;

@Controller
@RequiredArgsConstructor
public class IntroController {

    private final CategoryRepository categoryRepository;

    @GetMapping(value = {"/{catLevel1:intro}/{catLevel2}/{catLevel3}", "/{catLevel1:intro}/{catLevel2}"})
    public String intro(Model model , @ModelAttribute("categoryDTOList") List<CategoryDTO> categoryDTOList
                        ,@PathVariable(name = "catLevel1") String catLevel1
                        ,@PathVariable(name = "catLevel2") String catLevel2
                        ,@PathVariable(name = "catLevel3", required = false) String catLevel3){

        CategoryRouterResult categoryRouterResult = setCategoryAndRoute(model, categoryDTOList, catLevel1, catLevel2, catLevel3);

        CategoryRouter router = categoryRouterResult.router();
        CategoryDTO categoryLevel3 = categoryRouterResult.categoryLevel3();

        return router.routing(categoryLevel3);
    }




}
