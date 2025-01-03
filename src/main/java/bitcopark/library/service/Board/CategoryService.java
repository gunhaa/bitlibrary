package bitcopark.library.service.Board;

import bitcopark.library.entity.Board.Category;
import bitcopark.library.exception.CategoryNotFoundException;
import bitcopark.library.repository.Board.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    // ADMIN만 사용가능한 메서드
    @Transactional
    public Category createNewCategory(String categoryName){
        Category category = Category.builder()
                .categoryName(categoryName)
                .build();

        validateDuplicateCategoryName(categoryName);

        categoryRepository.save(category);
        return category;
    }

    @Transactional
    public Category createNewCategoryWithParentCategory(String categoryName, Category parentCategory){
        Category category = Category.builder()
                .categoryName(categoryName)
                .parentCategory(parentCategory)
                .build();

        validateDuplicateCategoryName(categoryName);

        categoryRepository.save(category);
        return category;
    }

    public Category createCategoryObject(String categoryName){
        return categoryRepository.findByCategoryName(categoryName).orElseThrow(()-> new CategoryNotFoundException("존재하지 않는 카테고리 입니다 : " + categoryName));
    }

    private void validateDuplicateCategoryName(String categoryName) {
        if(categoryRepository.existsByCategoryName(categoryName)){
            throw new CategoryNotFoundException("중복된 카테고리 입니다 : " + categoryName);
        }
    }

}
