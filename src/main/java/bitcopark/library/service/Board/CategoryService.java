package bitcopark.library.service.Board;

import bitcopark.library.aop.CategoryDTO;
import bitcopark.library.entity.board.Category;
import bitcopark.library.exception.CategoryNotFoundException;
import bitcopark.library.repository.Board.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    // ADMIN만 사용가능한 메서드
    @Transactional
    public Category createNewCategory(String categoryName, String categoryEngName) {
        Category category = Category.builder()
                .categoryName(categoryName)
                .categoryEngName(categoryEngName)
                .build();

        validateDuplicateCategoryName(categoryName);

        categoryRepository.save(category);
        return category;
    }

    @Transactional
    public Category createNewCategoryWithParentCategory(String categoryName, Category parentCategory, String categoryEngName) {
        Category category = Category.builder()
                .categoryName(categoryName)
                .parentCategory(parentCategory)
                .categoryEngName(categoryEngName)
                .build();

        validateDuplicateCategoryName(categoryName);

        categoryRepository.save(category);
        return category;
    }

    public Category createCategoryObject(String categoryName) {
        return categoryRepository.findByCategoryName(categoryName).orElseThrow(() -> new CategoryNotFoundException("존재하지 않는 카테고리 입니다 : " + categoryName));
    }

    private void validateDuplicateCategoryName(String categoryName) {
        if (categoryRepository.existsByCategoryName(categoryName)) {
            throw new CategoryNotFoundException("중복된 카테고리 입니다 : " + categoryName);
        }
    }


    public List<CategoryDTO> getCategoryDTOList(List<Category> categoryList) {
        return categoryList.stream()
                .map(category -> new CategoryDTO(category.getId(),
                        category.getCategoryName(),
                        category.getParentCategory() != null ? category.getParentCategory() : null,
                        category.getSubCategory(),
                        category.getBoardList(),
                        category.getCategoryEngName(),
                        getFirstSubCategory(category.getSubCategory().isEmpty() ? null : category.getSubCategory())))
                .toList();
    }

    private Long getFirstSubCategory(List<Category> subCategory) {
        Long firstCategoryId = null;
        if(subCategory != null){
            firstCategoryId = subCategory.get(0).getId();
        }
        return firstCategoryId;
    }

    public Category getIdByEngName(String engName) {
        return categoryRepository.findByCategoryEngName(engName).orElseThrow(() -> new CategoryNotFoundException("존재하지 않는 카테고리 입니다  : " + engName));
    }
}