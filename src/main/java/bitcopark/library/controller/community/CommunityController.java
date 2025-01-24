package bitcopark.library.controller.community;

import bitcopark.library.aop.CategoryDTO;
import bitcopark.library.controller.util.ControllerUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

import static bitcopark.library.controller.util.ControllerUtils.setCategoryAndRoute;

@Controller
public class CommunityController {

    @GetMapping(value="{catLevel1:community}/{catLevel2}")
    public String community(Model model, @ModelAttribute("categoryDTOList") List<CategoryDTO> categoryDTOList
            ,@PathVariable(name = "catLevel1") String catLevel1
            ,@PathVariable(name = "catLevel2") String catLevel2) {

        ControllerUtils.CategoryRouterResult categoryRouterResult = setCategoryAndRoute(model, categoryDTOList, catLevel1, catLevel2, null);

        return categoryRouterResult.router().routing(categoryRouterResult.categoryLevel3());
    }

}
