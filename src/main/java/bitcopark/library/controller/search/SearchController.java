package bitcopark.library.controller.search;

import bitcopark.library.aop.CategoryDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class SearchController {

    @GetMapping(value = {"/{catLevel1:search}/{catLevel2}/{catLevel3}" , "/{catLevel1:search}/{catLevel2}/"})
    public String search(Model model, @ModelAttribute("categoryDTOList") List<CategoryDTO> categoryDTOList,
                         @PathVariable(name = "catLevel1") String catLevel1,
                         @PathVariable(name = "catLevel2") String catLevel2,
                         @PathVariable(name = "catLevel3", required = false) String catLevel3){

        return "";
    }

}
