package bitcopark.library.service.Board;

import bitcopark.library.controller.aop.CategoryDTO;
import bitcopark.library.entity.Board.Category;
import bitcopark.library.exception.CategoryNotFoundException;
import bitcopark.library.repository.Board.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    // ADMIN만 사용가능한 메서드
    @Transactional
    public Category createNewCategory(String categoryName){
        Category category = Category.builder()
                .categoryName(categoryName)
                .build();

        validateDuplicateCategoryName(categoryName);

        categoryRepository.save(category);
        return category;
    }

    @Transactional
    public Category createNewCategoryWithParentCategory(String categoryName, Category parentCategory){
        Category category = Category.builder()
                .categoryName(categoryName)
                .parentCategory(parentCategory)
                .build();

        validateDuplicateCategoryName(categoryName);

        categoryRepository.save(category);
        return category;
    }

    public Category createCategoryObject(String categoryName){
        return categoryRepository.findByCategoryName(categoryName).orElseThrow(()-> new CategoryNotFoundException("존재하지 않는 카테고리 입니다 : " + categoryName));
    }

    private void validateDuplicateCategoryName(String categoryName) {
        if(categoryRepository.existsByCategoryName(categoryName)){
            throw new CategoryNotFoundException("중복된 카테고리 입니다 : " + categoryName);
        }
    }



    public List<CategoryDTO> getCategoryDTOList(List<Category> categoryList){
        return categoryList.stream()
                .map(category -> new CategoryDTO(category.getId(),
                        category.getCategoryName(),
                        category.getParentCategory() != null ? category.getParentCategory() : null,
                        category.getSubCategory(),
                        category.getBoardList(),
                        mapKorToEngCategoryName(category.getCategoryName())))
                .toList();
    }

    private String mapKorToEngCategoryName(String categoryName) {
        Map<String, String> nameMap = new HashMap<>();
        nameMap.put("도서관 안내", "intro");
        nameMap.put("자료 검색", "search");
        nameMap.put("참여 마당", "community");
        nameMap.put("이용자 마당", "user");
        nameMap.put("맛있는 도서관", "deliciousLibrary");
        nameMap.put("내 서재", "myLibrary");
        nameMap.put("도서관 소개", "introduction");
        nameMap.put("이용안내", "guide");
        nameMap.put("시설안내", "facilityGuide");
        nameMap.put("통합자료 검색", "integratedSearch");
        nameMap.put("희망 도서 신청", "wishBookRequest");
        nameMap.put("교육문화 프로그램", "eduCultureProgram");
        nameMap.put("열람실", "readingRoom");
        nameMap.put("세미나실", "seminarRoom");
        nameMap.put("공지사항", "notice");
        nameMap.put("문의사항", "inquiries");
        nameMap.put("자주 묻는 질문", "faq");
        nameMap.put("책 후기 나눠요", "bookReviews");
        nameMap.put("구내식당", "cafeteria");
        nameMap.put("카페", "cafe");
        nameMap.put("나의 도서관", "myLibrary");
        nameMap.put("결제내역", "paymentHistory");
        nameMap.put("회원정보", "memberInfo");
        nameMap.put("인사말", "greetings");
        nameMap.put("연혁", "history");
        nameMap.put("조직도", "organizationChart");
        nameMap.put("도서관 오시는 길", "directions");
        nameMap.put("주변 도서관", "nearbyLibraries");
        nameMap.put("이용시간", "operatingHours");
        nameMap.put("도서관 달력", "libraryCalendar");
        nameMap.put("간략 검색", "quickSearch");
        nameMap.put("상세 검색", "detailedSearch");
        nameMap.put("도서현황", "bookStatus");
        nameMap.put("예약 및 신청", "reservationAndRequests");
        nameMap.put("즐겨찾기", "favorites");
        nameMap.put("내 정보", "myInfo");
        nameMap.put("정보수정", "editInfo");
        nameMap.put("내 글 관리", "myPosts");
        nameMap.put("비밀번호 변경", "changePassword");
        nameMap.put("회원탈퇴", "membershipCancellation");
        nameMap.put("대출중인 도서", "booksOnLoan");
        nameMap.put("이전 대출내역", "previousLoanHistory");
        nameMap.put("예약내역", "reservationHistory");
        nameMap.put("신청내역", "applicationHistory");
        nameMap.put("좌석 예약현황", "seatReservationStatus");
        nameMap.put("공간 예약현황", "spaceReservationStatus");
        nameMap.put("클래스 신청현황", "classApplicationStatus");
        nameMap.put("게시글", "posts");
        nameMap.put("댓글", "comments");

        return nameMap.get(categoryName);
    }

}
