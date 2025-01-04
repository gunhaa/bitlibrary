package bitcopark.library.controller.intro;

import bitcopark.library.controller.advice.CategoryDTO;
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

@Controller
@RequiredArgsConstructor
public class IntroController {

    private final CategoryRepository categoryRepository;

//    @GetMapping("/intro/introduction")
    @GetMapping("/intro/{catLevel2}/{catLevel3}")
    public String intro(Model model , @ModelAttribute("categoryList") List<CategoryDTO> categoryDTOList
                        , @PathVariable String catLevel2, @PathVariable Integer catLevel3){

        String catLevel1 = "intro";
        CategoryDTO categoryDTO1 = getCategoryDTObyCategoryEngName(categoryDTOList, catLevel1);
        model.addAttribute("catLevel1", categoryDTO1.getId());


        CategoryDTO categoryDTO2 = getCategoryDTObyCategoryEngName(categoryDTOList, catLevel2);
        model.addAttribute("catLevel2", categoryDTO2.getId());

//        CategoryDTO categoryDTO3 = getCategoryDTObyCategoryId(categoryDTOList, catLevel3);
        model.addAttribute("catLevel3", catLevel3);

        System.out.println("catLevel3 = " + catLevel3);
//        System.out.println("categoryDTO3.getId() = " + categoryDTO3.getId());
        
        if(categoryDTO2.getCategoryName().equals("도서관 소개")){
            return "/intro/lib_greeting";
        }

        if(categoryDTO2.getCategoryName().equals("이용안내")) {
//            model.addAttribute("catLevel3", 29);
            return "intro/lib_hours";
        }

        if(categoryDTO2.getCategoryName().equals("시설안내")) {
            return "intro/lib_map";
        }



//            String category1 = "도서관 안내";
//            String category2 = "도서관 소개";
//            String category3 = "인사말";
//
//            List<Category> categories = categoryRepository.selectCategories(category1, category2, category3);
//
//            for(int i=1; i < categories.size()+1; i++){
//            model.addAttribute("catLevel"+i, categories.get(i-1).getId());
//            }

        return "common/main";
    }

    private static CategoryDTO getCategoryDTObyCategoryEngName(List<CategoryDTO> categoryDTOList, String catLevel) {
        return categoryDTOList.stream()
                .filter(categoryDTO -> categoryDTO.getCategoryEngName().equals(catLevel))
                .findFirst().orElseThrow(CategoryNotFoundException::new);
    }

    private static CategoryDTO getCategoryDTObyCategoryId(List<CategoryDTO> categoryDTOList, String catLevel){
        return categoryDTOList.stream()
                .filter(categoryDTO -> categoryDTO.getId().intValue() == Integer.parseInt(catLevel))
                .findFirst().orElseThrow(CategoryNotFoundException::new);
    }


}
