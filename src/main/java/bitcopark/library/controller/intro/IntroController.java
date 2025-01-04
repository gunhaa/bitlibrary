package bitcopark.library.controller.intro;

import bitcopark.library.entity.Board.Category;
import bitcopark.library.repository.Board.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class IntroController {

    private final CategoryRepository categoryRepository;

    @GetMapping("/intro/guide")
    public String intro(Model model){
        String category1 = "도서관 안내";
        String category2 = "도서관 소개";
        String category3 = "인사말";

        List<Category> categories = categoryRepository.selectCategories(category1, category2, category3);

        for(int i=1; i < categories.size()+1; i++){
            System.out.println("i = " + i);
            System.out.println("categories.get(i-1).getId() = " + categories.get(i-1).getId());
            model.addAttribute("catLevel"+i, categories.get(i-1).getId());
        }
//        model.addAttribute("catLevel1", 1);
//        model.addAttribute("catLevel2", 7);
//        model.addAttribute("catLevel3", 24);
        return "/intro/lib_greeting";
    }

}
