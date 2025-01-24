package bitcopark.library.categoryStrategy;

import bitcopark.library.aop.CategoryDTO;

public class StudyStrategy implements CategoryStrategy {
    @Override
    public String routing(CategoryDTO categoryLevel3) {
        return switch (categoryLevel3.getCategoryName()) {
            case "도서현황", "예약 및 신청" -> subRouting(categoryLevel3.getSubCategoryEngName());
            case "즐겨찾기" -> "my/library/bookmark/bookmark.html";
            default -> "common/main";
        };
    }

    public String subRouting(String name) {
        return switch (name) {
            case "books-on-loan" -> "my/library/book/booksLoan.html";
            case "previous-loan-history" -> "my/library/book/loanHistory.html";
            case "reservation-history" -> "my/library/book/reservationBook.html";
            case "application-history" -> "my/library/book/bookRequestHistory.html";
            case "seat-reservation-status" -> "my/library/reserv/readingRoom.html";
            case "space-reservation-status" -> "my/library/reserv/seminar.html";
            case "class-application-status" -> "my/library/reserv/class.html";
            default -> "common/main";
        };
    }
}
