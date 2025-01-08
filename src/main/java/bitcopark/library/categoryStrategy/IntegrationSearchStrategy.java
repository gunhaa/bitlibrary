package bitcopark.library.categoryStrategy;

import bitcopark.library.aop.CategoryDTO;

public class IntegrationSearchStrategy implements CategoryStrategy {
    @Override
    public String routing(CategoryDTO categoryLevel3) {
        return "search/search";
    }
}
