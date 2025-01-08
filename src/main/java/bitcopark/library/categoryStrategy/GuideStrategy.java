package bitcopark.library.categoryStrategy;

import bitcopark.library.aop.CategoryDTO;

public class GuideStrategy implements CategoryStrategy {

    @Override
    public String routing(CategoryDTO categoryLevel3) {
        return switch (categoryLevel3.getCategoryName()) {
            case "이용시간" -> "intro/lib_hours";
            case "도서관 달력" -> "intro/lib_calender";
            default -> "common/main";
        };
    }

}
