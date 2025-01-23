package bitcopark.library.aop;

import bitcopark.library.entity.board.Category;
import bitcopark.library.repository.board.CategoryRepository;
import bitcopark.library.service.Board.CategoryService;
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
    private final CategoryService categoryService;

    @ModelAttribute("categoryDTOList")
    public List<CategoryDTO> addGlobalCategory() {

        @SuppressWarnings("unchecked")
        List<CategoryDTO> categoryDTOList = (List<CategoryDTO>) servletContext.getAttribute("categoryDTOList");

        if (categoryDTOList == null) {
            List<Category> categoryList = categoryRepository.selectAll();
            categoryDTOList = categoryService.getCategoryDTOList(categoryList);
            servletContext.setAttribute("categoryDTOList", categoryDTOList);
        }

        return categoryDTOList;
    }


}
