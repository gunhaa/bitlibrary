package bitcopark.library.categoryStrategy;

import bitcopark.library.aop.CategoryDTO;
import org.springframework.ui.Model;

public interface CategoryStrategy {

    String routing(CategoryDTO categoryLevel3);

}
