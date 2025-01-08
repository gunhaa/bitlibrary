package bitcopark.library.categoryStrategy;

import bitcopark.library.aop.CategoryDTO;

public class FacilityStrategy implements CategoryStrategy {
    @Override
    public String routing(CategoryDTO categoryLevel3) {
        return "intro/lib_map";
    }
}
