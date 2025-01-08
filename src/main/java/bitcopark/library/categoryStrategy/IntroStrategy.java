package bitcopark.library.categoryStrategy;

import bitcopark.library.aop.CategoryDTO;

public class IntroStrategy implements CategoryStrategy{

    @Override
    public String routing(CategoryDTO categoryLevel3) {
        return switch(categoryLevel3.getCategoryName()){
            case "인사말" -> "/intro/lib_greeting";
            case "연혁" -> "intro/lib_history";
            case "조직도" -> "intro/lib_organization";
            case "도서관 오시는 길" -> "intro/lib_intro";
            case "주변 도서관" -> "intro/lib_intro_another";
            default -> "common/main";
        };
    }

}
