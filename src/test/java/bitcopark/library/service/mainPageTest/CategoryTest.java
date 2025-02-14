package bitcopark.library.service.mainPageTest;

import bitcopark.library.entity.board.Category;
import bitcopark.library.repository.board.CategoryRepository;
import bitcopark.library.service.board.CategoryService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest
public class CategoryTest {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CategoryRepository categoryRepository;

    @DisplayName("카테고리_생성")
    @Test
    public void 카테고리_생성(){

        // Given
        String categoryName = "카테고리";
        String categoryEngName = "category";
        Category category = categoryService.createNewCategory(categoryName, categoryEngName);

        // When
        boolean isExist = categoryRepository.existsByCategoryName(categoryName);

        // Then
        Assertions.assertThat(isExist).isTrue();
    }



}
