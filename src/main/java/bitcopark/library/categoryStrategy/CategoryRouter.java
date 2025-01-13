package bitcopark.library.categoryStrategy;

import bitcopark.library.aop.CategoryDTO;

public class CategoryRouter {

    private final CategoryStrategy categoryStrategy;

    public CategoryRouter(CategoryStrategy categoryStrategy) {
        this.categoryStrategy = categoryStrategy;
    }

    public String routing(CategoryDTO categoryLevel3){
        if (categoryStrategy == null) {
            throw new IllegalStateException("CategoryStrategy is not set!");
        }
        return categoryStrategy.routing(categoryLevel3);
    }

}
