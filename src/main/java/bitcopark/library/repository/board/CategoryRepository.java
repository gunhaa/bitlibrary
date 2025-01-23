package bitcopark.library.repository.board;

import bitcopark.library.entity.board.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    Optional<Category> findByCategoryName(String categoryName);

    boolean existsByCategoryName(String categoryName);

    @Query("select count(c) from Category c")
    int selectCategoryCount();

    @Query("select c from Category c left join fetch c.parentCategory and left join fetch c.subCategory")
    List<Category> selectAll();

    @Query("select c from Category c where c.categoryName=:category1 or c.categoryName=:category2 or c.categoryName=:category3 order by c.id asc")
    List<Category> selectCategories(String category1, String category2, String category3);

    Optional<Category> findByCategoryEngName(String catagoryEngName);
}