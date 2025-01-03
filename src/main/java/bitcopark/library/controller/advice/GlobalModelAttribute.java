package bitcopark.library.controller.advice;

import bitcopark.library.entity.Board.Category;
import bitcopark.library.repository.Board.CategoryRepository;
import jakarta.servlet.ServletContext;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

@ControllerAdvice
@RequiredArgsConstructor
public class GlobalModelAttribute {

    private final ServletContext servletContext;
    private final CategoryRepository categoryRepository;

    @ModelAttribute("categoryList")
    public List<Category> addGlobalCategory() {

        System.out.println("이곳 실행됨");
        
        @SuppressWarnings("unchecked")
        List<Category> categoryList = (List<Category>) servletContext.getAttribute("categoryList");
        
        if (categoryList == null) {
            System.out.println("category가 널값임 왜이럼??");
            categoryList = categoryRepository.selectAll();
            System.out.println("categoryList = " + categoryList);
            servletContext.setAttribute("categoryList", categoryList);
        }

        return categoryList;
    }
}
