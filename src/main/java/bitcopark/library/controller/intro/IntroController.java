package bitcopark.library.controller.intro;

import bitcopark.library.controller.aop.CategoryDTO;
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


    @GetMapping("/{catLevel1:intro}/{catLevel2}/{catLevel3}")
    public String intro(Model model , @ModelAttribute("categoryList") List<Category> categoryList
                        ,@PathVariable String catLevel1, @PathVariable String catLevel2, @PathVariable String catLevel3){

        System.out.println("해당 컨트롤러 사용됨");
        System.out.println("catLevel1 = " + catLevel1);
        System.out.println("catLevel1 = " + catLevel2);
        System.out.println("catLevel1 = " + catLevel3);

        Category categoryLevel1 = getCategoryByCategoryEngName(categoryList, catLevel1);
        System.out.println("categoryLevel1.getId() = " + categoryLevel1.getId());
        model.addAttribute("catLevel1", categoryLevel1.getId());

        Category categoryLevel2 = getCategoryByCategoryEngName(categoryList, catLevel2);
        System.out.println("categoryLevel2.getId() = " + categoryLevel2.getId());
        model.addAttribute("catLevel2", categoryLevel2.getId());

        if(categoryLevel2.getCategoryName().equals("도서관 소개")){
            Category categoryLevel3 = getCategoryByCategoryId(categoryList, catLevel3);
            model.addAttribute("catLevel3", Integer.parseInt(catLevel3));
            switch (categoryLevel3.getCategoryName()) {
                case "인사말" -> {
                    return "/intro/lib_greeting";
                }
                case "연혁" -> {
                    return "intro/lib_history";
                }
                case "조직도" -> {
                    return "intro/lib_organization";
                }
                case "도서관 오시는 길" -> {
                    return "intro/lib_intro";
                }
                case "주변 도서관" -> {
                    // do something
                    return "intro/lib_intro_another";
                }
            }

        }

        if(categoryLevel2.getCategoryName().equals("이용안내")) {
            Category categoryLevel3 = getCategoryByCategoryId(categoryList, catLevel3);
            model.addAttribute("catLevel3", Integer.parseInt(catLevel3));
            switch(categoryLevel3.getCategoryName()){
                case "이용시간" -> {
                    return "intro/lib_hours";
                }
                case "도서관 달력" -> {
                    return "intro/lib_calender";
                }
            }
        }

        if(categoryLevel2.getCategoryName().equals("시설안내")) {

            // dosomething
            return "intro/lib_map";
        }

        return "common/main";
    }

    private static Category getCategoryByCategoryEngName(List<Category> categoryList, String catLevel) {
        return categoryList.stream()
                .filter(category -> category.getCategoryEngName().equals(catLevel))
                .findFirst().orElseThrow(CategoryNotFoundException::new);
    }

    private static Category getCategoryByCategoryId(List<Category> categoryList, String catLevel){
        return categoryList.stream()
                .filter(categoryDTO -> categoryDTO.getId().intValue() == Integer.parseInt(catLevel))
                .findFirst().orElseThrow(CategoryNotFoundException::new);
    }


}
