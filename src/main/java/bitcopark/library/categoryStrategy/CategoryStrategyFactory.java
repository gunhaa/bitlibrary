package bitcopark.library.categoryStrategy;

import bitcopark.library.aop.CategoryDTO;

public class CategoryStrategyFactory {

    public static CategoryStrategy getStrategy(CategoryDTO level2Category) {
        return switch (level2Category.getCategoryName()) {
            case "도서관 소개" -> new IntroStrategy();
            case "이용안내" -> new GuideStrategy();
            case "시설안내" -> new FacilityStrategy();
            default -> new MainStrategy();
        };
    }

}
