package bitcopark.library.repository.Board;

import bitcopark.library.entity.Board.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    Optional<Category> findByCategoryName(String categoryName);

    boolean existsByCategoryName(String categoryName);

    @Query("select count(c) from Category c")
    int selectCategoryCount();
}