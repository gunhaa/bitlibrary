package bitcopark.library.temp;

import bitcopark.library.entity.Board.Category;
import bitcopark.library.repository.Board.CategoryRepository;
import bitcopark.library.service.Board.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class tempCategoryGenerate implements ApplicationRunner {

    private final CategoryService categoryService;

    private final CategoryRepository categoryRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        // 기존 데이터 삭제
        categoryRepository.deleteAll();

        // 레벨 1 카테고리 생성
        Category 도서관_안내 = categoryService.createNewCategory("도서관 안내");
        Category 자료_검색 = categoryService.createNewCategory("자료 검색");
        Category 참여_마당 = categoryService.createNewCategory("참여 마당");
        Category 이용자_마당 = categoryService.createNewCategory("이용자 마당");
        Category 맛있는_도서관 = categoryService.createNewCategory("맛있는 도서관");
        Category 내_서재 = categoryService.createNewCategory("내 서재");

        // 레벨 2 카테고리 생성
        Category 도서관_소개 = categoryService.createNewCategoryWithParentCategory("도서관 소개", 도서관_안내);
        categoryService.createNewCategoryWithParentCategory("이용안내", 도서관_안내);
        categoryService.createNewCategoryWithParentCategory("시설안내", 도서관_안내);

        categoryService.createNewCategoryWithParentCategory("통합자료 검색", 자료_검색);
        categoryService.createNewCategoryWithParentCategory("희망 도서 신청", 자료_검색);

        categoryService.createNewCategoryWithParentCategory("교육문화 프로그램", 참여_마당);
        categoryService.createNewCategoryWithParentCategory("열람실", 참여_마당);
        categoryService.createNewCategoryWithParentCategory("세미나실", 참여_마당);

        categoryService.createNewCategoryWithParentCategory("공지사항", 이용자_마당);
        categoryService.createNewCategoryWithParentCategory("문의사항", 이용자_마당);
        categoryService.createNewCategoryWithParentCategory("자주 묻는 질문", 이용자_마당);
        categoryService.createNewCategoryWithParentCategory("책 후기 나눠요", 이용자_마당);

        categoryService.createNewCategoryWithParentCategory("구내식당", 맛있는_도서관);
        categoryService.createNewCategoryWithParentCategory("카페", 맛있는_도서관);

        categoryService.createNewCategoryWithParentCategory("나의 도서관", 내_서재);
        categoryService.createNewCategoryWithParentCategory("결제내역", 내_서재);
        categoryService.createNewCategoryWithParentCategory("회원정보", 내_서재);

        // 레벨 3 카테고리 생성
        categoryService.createNewCategoryWithParentCategory("인사말", 도서관_소개);
        categoryService.createNewCategoryWithParentCategory("연혁", 도서관_소개);
        categoryService.createNewCategoryWithParentCategory("조직도", 도서관_소개);
        categoryService.createNewCategoryWithParentCategory("도서관 오시는 길", 도서관_소개);
        categoryService.createNewCategoryWithParentCategory("주변 도서관", 도서관_소개);

        categoryService.createNewCategoryWithParentCategory("이용시간", 도서관_소개);
        categoryService.createNewCategoryWithParentCategory("도서관 달력", 도서관_소개);

        categoryService.createNewCategoryWithParentCategory("간략 검색", 자료_검색);
        categoryService.createNewCategoryWithParentCategory("상세 검색", 자료_검색);

        categoryService.createNewCategoryWithParentCategory("도서현황", 자료_검색);
        categoryService.createNewCategoryWithParentCategory("예약 및 신청", 자료_검색);
        categoryService.createNewCategoryWithParentCategory("즐겨찾기", 자료_검색);

        categoryService.createNewCategoryWithParentCategory("내 정보", 내_서재);
        categoryService.createNewCategoryWithParentCategory("정보수정", 내_서재);
        categoryService.createNewCategoryWithParentCategory("내 글 관리", 내_서재);
        categoryService.createNewCategoryWithParentCategory("비밀번호 변경", 내_서재);
        categoryService.createNewCategoryWithParentCategory("회원탈퇴", 내_서재);

        // 레벨 4 카테고리 생성
        categoryService.createNewCategoryWithParentCategory("대출중인 도서", 내_서재);
        categoryService.createNewCategoryWithParentCategory("이전 대출내역", 내_서재);
        categoryService.createNewCategoryWithParentCategory("예약내역", 내_서재);
        categoryService.createNewCategoryWithParentCategory("신청내역", 내_서재);

        categoryService.createNewCategoryWithParentCategory("좌석 예약현황", 참여_마당);
        categoryService.createNewCategoryWithParentCategory("공간 예약현황", 참여_마당);
        categoryService.createNewCategoryWithParentCategory("클래스 신청현황", 참여_마당);

        categoryService.createNewCategoryWithParentCategory("게시글", 이용자_마당);
        categoryService.createNewCategoryWithParentCategory("댓글", 이용자_마당);

    }
}
