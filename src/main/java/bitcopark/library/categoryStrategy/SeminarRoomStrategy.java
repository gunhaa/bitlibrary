package bitcopark.library.categoryStrategy;

import bitcopark.library.aop.CategoryDTO;

public class SeminarRoomStrategy implements CategoryStrategy{
    @Override
    public String routing(CategoryDTO categoryLevel3) {
        return "community/seminar-room";
    }
}
