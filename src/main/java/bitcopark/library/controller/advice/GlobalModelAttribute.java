package bitcopark.library.controller.advice;

import bitcopark.library.entity.Board.Category;
import bitcopark.library.repository.Board.CategoryRepository;
import bitcopark.library.service.Board.CategoryService;
import jakarta.servlet.ServletContext;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
@RequiredArgsConstructor
public class GlobalModelAttribute {

    private final ServletContext servletContext;
    private final CategoryRepository categoryRepository;
    private final CategoryService categoryService;

    @ModelAttribute("categoryList")
    public List<CategoryDTO> addGlobalCategory() {

        @SuppressWarnings("unchecked")
        List<CategoryDTO> categoryDTOList = (List<CategoryDTO>) servletContext.getAttribute("categoryList");
        
        if (categoryDTOList == null) {
            List<Category> categoryList = categoryRepository.selectAll();
            categoryDTOList = categoryService.getCategoryDTOList(categoryList);

            servletContext.setAttribute("categoryList", categoryDTOList);
        }

        return categoryDTOList;
    }


}
