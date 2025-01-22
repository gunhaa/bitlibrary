package bitcopark.library.categoryStrategy;

import bitcopark.library.aop.CategoryDTO;

public class FaqStrategy implements CategoryStrategy {
    @Override
    public String routing(CategoryDTO categoryLevel3) {
        return "user/faq";
    }
}
