package bitcopark.library.categoryStrategy;

import bitcopark.library.aop.CategoryDTO;

public class MainStrategy implements CategoryStrategy{
    @Override
    public String routing(CategoryDTO categoryLevel3) {
        return "common/main";
    }
}
