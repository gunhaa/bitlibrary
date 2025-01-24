package bitcopark.library.categoryStrategy;

import bitcopark.library.aop.CategoryDTO;

public class EduCultureProgram implements CategoryStrategy{
    @Override
    public String routing(CategoryDTO categoryLevel3) {
        return "community/edu-culture-program";
    }
}
