package bitcopark.library.categoryStrategy;

import bitcopark.library.aop.CategoryDTO;

public class CategoryStrategyFactory {

    public static CategoryStrategy getStrategy(CategoryDTO level2Category) {
        return switch (level2Category.getCategoryName()) {
            case "도서관 소개" -> new IntroStrategy();
            case "이용안내" -> new GuideStrategy();
            case "시설안내" -> new FacilityStrategy();
            case "통합자료 검색" -> new SearchStrategy();
            case "희망 도서 신청" -> new RequestBookStrategy();
            default -> new MainStrategy();
        };
    }

}
