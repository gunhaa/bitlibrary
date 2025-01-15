package bitcopark.library.aop;

import bitcopark.library.entity.Board.Board;
import bitcopark.library.entity.Board.Category;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CategoryDTO {
    private Long id;
    private String categoryName;
    private Category parentCategory;
    private List<Category> subCategory = new ArrayList<>();
    private List<Board> boardList = new ArrayList<>();
    private String categoryEngName;
    private Long firstSubCategoryId;
    private String subCategoryEngName;

    public CategoryDTO(Long id, String categoryName, Category parentCategory, List<Category> subCategory, List<Board> boardList, String categoryEngName, Long firstSubCategoryId) {
        this.id = id;
        this.categoryName = categoryName;
        this.parentCategory = parentCategory;
        this.subCategory = subCategory;
        this.boardList = boardList;
        this.categoryEngName = categoryEngName;
        this.firstSubCategoryId = firstSubCategoryId;
    }
}
