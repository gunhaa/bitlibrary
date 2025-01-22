package bitcopark.library.categoryStrategy;

import bitcopark.library.aop.CategoryDTO;

public class BookReviewStrategy implements CategoryStrategy{
    @Override
    public String routing(CategoryDTO categoryLevel3) {
        return "user/book-reviews";
    }
}
