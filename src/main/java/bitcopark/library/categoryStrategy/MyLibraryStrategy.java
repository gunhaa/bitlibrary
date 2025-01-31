package bitcopark.library.categoryStrategy;

import bitcopark.library.aop.CategoryDTO;

public class MyLibraryStrategy implements CategoryStrategy {
    @Override
    public String routing(CategoryDTO categoryLevel3) {
        return switch (categoryLevel3.getCategoryName()) {
            case "도서현황" -> "study/library/book-status.html";
            case "예약 및 신청" -> "study/library/reservation-application.html";
            case "즐겨찾기" -> "study/library/favorite-books.html";
            default -> "common/main";
        };
    }
}
