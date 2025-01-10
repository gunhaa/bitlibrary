package bitcopark.library.categoryStrategy;

import bitcopark.library.aop.CategoryDTO;

public class SearchStrategy implements CategoryStrategy {
    @Override
    public String routing(CategoryDTO categoryLevel3) {
        return switch (categoryLevel3.getCategoryName()) {
            case "간략 검색" -> "search/search";
            case "상세 검색" -> "search/searchDetail";
            default -> "common/main";
        };
    }
}