package bitcopark.library.categoryStrategy;

import bitcopark.library.aop.CategoryDTO;

public class RequestBookStrategy implements CategoryStrategy {
    @Override
    public String routing(CategoryDTO categoryLevel3) {
        return "search/bookRequest";
    }
}
