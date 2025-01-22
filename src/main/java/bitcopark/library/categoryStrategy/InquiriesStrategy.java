package bitcopark.library.categoryStrategy;

import bitcopark.library.aop.CategoryDTO;

public class InquiriesStrategy implements CategoryStrategy{
    @Override
    public String routing(CategoryDTO categoryLevel3) {
        return "user/inquiries";
    }
}
