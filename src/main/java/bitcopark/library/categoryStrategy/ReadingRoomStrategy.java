package bitcopark.library.categoryStrategy;

import bitcopark.library.aop.CategoryDTO;

public class ReadingRoomStrategy implements CategoryStrategy{
    @Override
    public String routing(CategoryDTO categoryLevel3) {
        return "community/reading-room";
    }
}
