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
            case "나의 도서관" -> new StudyStrategy();
            case "공지사항" -> new NoticeStrategy();
            case "문의사항" -> new InquiriesStrategy();
            case "자주 묻는 질문" -> new FaqStrategy();
            case "책 후기 나눠요" -> new BookReviewStrategy();
            case "교육문화 프로그램" -> new EduCultureProgramStrategy();
            case "열람실" -> new ReadingRoomStrategy();
            case "세미나실" -> new SeminarRoomStrategy();
            default -> new MainStrategy();
        };
    }

}
