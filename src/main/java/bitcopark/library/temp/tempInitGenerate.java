package bitcopark.library.temp;

import bitcopark.library.entity.Board.Category;
import bitcopark.library.entity.Book.BookState;
import bitcopark.library.entity.Book.BookSupple;
import bitcopark.library.repository.Board.CategoryRepository;
import bitcopark.library.repository.Book.BookRepository;
import bitcopark.library.service.Board.CategoryService;
import bitcopark.library.service.Book.BookService;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Profile("local")
@Component
@RequiredArgsConstructor
public class tempInitGenerate {

    // 해당 클래스는 로컬환경에서 테스트 데이터 삽입을 위한 클래스이다
    // 배포시 제거해야한다.

    private final initService initService;

    @PostConstruct
    public void init(){
        initService.init();
    }

    @Component
    @RequiredArgsConstructor
    private static class initService {

        private final CategoryService categoryService;
        private final CategoryRepository categoryRepository;

        private final BookService bookService;
        private final BookRepository bookRepository;

        @Transactional
        public void init(){

            if(categoryRepository.selectCategoryCount()!=49){
                // 기존 데이터 삭제
                categoryRepository.deleteAll();
                // 레벨 1 카테고리 생성
                Category 도서관_안내 = categoryService.createNewCategory("도서관 안내", "intro");
                Category 자료_검색 = categoryService.createNewCategory("자료 검색", "search");
                Category 참여_마당 = categoryService.createNewCategory("참여 마당", "community");
                Category 이용자_마당 = categoryService.createNewCategory("이용자 마당", "user");
                Category 맛있는_도서관 = categoryService.createNewCategory("맛있는 도서관", "delicious-library");
                Category 내_서재 = categoryService.createNewCategory("내 서재", "my-library");

                // 레벨 2 카테고리 생성
                Category 도서관_소개 = categoryService.createNewCategoryWithParentCategory("도서관 소개", 도서관_안내, "introduction");
                Category 이용안내 = categoryService.createNewCategoryWithParentCategory("이용안내", 도서관_안내, "guide");
                categoryService.createNewCategoryWithParentCategory("시설안내", 도서관_안내, "facility-guide");

                Category 통합자료_검색 = categoryService.createNewCategoryWithParentCategory("통합자료 검색", 자료_검색, "books");
                categoryService.createNewCategoryWithParentCategory("희망 도서 신청", 자료_검색, "book-req");

                categoryService.createNewCategoryWithParentCategory("교육문화 프로그램", 참여_마당, "edu-culture-program");
                categoryService.createNewCategoryWithParentCategory("열람실", 참여_마당, "reading-room");
                categoryService.createNewCategoryWithParentCategory("세미나실", 참여_마당, "seminar-room");

                categoryService.createNewCategoryWithParentCategory("공지사항", 이용자_마당, "notice");
                categoryService.createNewCategoryWithParentCategory("문의사항", 이용자_마당, "inquiries");
                categoryService.createNewCategoryWithParentCategory("자주 묻는 질문", 이용자_마당, "faq");
                categoryService.createNewCategoryWithParentCategory("책 후기 나눠요", 이용자_마당, "book-reviews");

                categoryService.createNewCategoryWithParentCategory("구내식당", 맛있는_도서관, "cafeteria");
                categoryService.createNewCategoryWithParentCategory("카페", 맛있는_도서관, "cafe");

                Category 나의_도서관 = categoryService.createNewCategoryWithParentCategory("나의 도서관", 내_서재, "my-library");
                categoryService.createNewCategoryWithParentCategory("결제내역", 내_서재, "payment-history");
                Category 회원정보 = categoryService.createNewCategoryWithParentCategory("회원정보", 내_서재, "member-info");

                // 레벨 3 카테고리 생성
                categoryService.createNewCategoryWithParentCategory("인사말", 도서관_소개, "greetings");
                categoryService.createNewCategoryWithParentCategory("연혁", 도서관_소개, "history");
                categoryService.createNewCategoryWithParentCategory("조직도", 도서관_소개, "organization-chart");
                categoryService.createNewCategoryWithParentCategory("도서관 오시는 길", 도서관_소개, "directions");
                categoryService.createNewCategoryWithParentCategory("주변 도서관", 도서관_소개, "nearby-libraries");

                categoryService.createNewCategoryWithParentCategory("이용시간", 이용안내, "operating-hours");
                categoryService.createNewCategoryWithParentCategory("도서관 달력", 이용안내, "library-calendar");

                categoryService.createNewCategoryWithParentCategory("간략 검색", 통합자료_검색, "quick-search");
                categoryService.createNewCategoryWithParentCategory("상세 검색", 통합자료_검색, "detailed-search");

                Category 도서현황 = categoryService.createNewCategoryWithParentCategory("도서현황", 나의_도서관, "book-status");
                Category 예약_및_신청 = categoryService.createNewCategoryWithParentCategory("예약 및 신청", 나의_도서관, "reservation-and-requests");
                categoryService.createNewCategoryWithParentCategory("즐겨찾기", 나의_도서관, "favorites");

                categoryService.createNewCategoryWithParentCategory("내 정보", 회원정보, "my-info");
                categoryService.createNewCategoryWithParentCategory("정보수정", 회원정보, "edit-info");
                categoryService.createNewCategoryWithParentCategory("내 글 관리", 회원정보, "my-posts");
                categoryService.createNewCategoryWithParentCategory("비밀번호 변경", 회원정보, "change-password");
                categoryService.createNewCategoryWithParentCategory("회원탈퇴", 회원정보, "membership-cancellation");

                // 레벨 4 카테고리 생성
                categoryService.createNewCategoryWithParentCategory("대출중인 도서", 도서현황, "books-on-loan");
                categoryService.createNewCategoryWithParentCategory("이전 대출내역", 도서현황, "previous-loan-history");
                categoryService.createNewCategoryWithParentCategory("예약내역", 도서현황, "reservation-history");
                categoryService.createNewCategoryWithParentCategory("신청내역", 도서현황, "application-history");

                categoryService.createNewCategoryWithParentCategory("좌석 예약현황", 예약_및_신청, "seat-reservation-status");
                categoryService.createNewCategoryWithParentCategory("공간 예약현황", 예약_및_신청, "space-reservation-status");
                categoryService.createNewCategoryWithParentCategory("클래스 신청현황", 예약_및_신청, "class-application-status");

                categoryService.createNewCategoryWithParentCategory("게시글", 회원정보, "posts");
                categoryService.createNewCategoryWithParentCategory("댓글", 회원정보, "comments");

            }

            if(bookRepository.selectAll().isEmpty()) {

                bookService.registerNewBook("박은몽 글", "명품 인생을 살아라", "대교베텔스만", "2008", "9788957592472", "https://search1.kakaocdn.net/thumb/R120x174.q85/?fname=http%3A%2F%2Ft1.daumcdn.net%2Flbook%2Fimage%2F762800%3Ftimestamp%3D20220904155651" , BookState.P, BookSupple.N );
                bookService.registerNewBook("김구 저", "백범일지", "돌베개", "2008", "9788971992258", "https://search1.kakaocdn.net/thumb/R120x174.q85/?fname=http%3A%2F%2Ft1.daumcdn.net%2Flbook%2Fimage%2F994701%3Ftimestamp%3D20240727112932", BookState.P, BookSupple.N);
                bookService.registerNewBook("댄 브라운 지음", "디셉션 포인트", "대교베텔스만", "2006", "9788957591574", "https://search1.kakaocdn.net/thumb/R120x174.q85/?fname=http%3A%2F%2Ft1.daumcdn.net%2Flbook%2Fimage%2F762541%3Ftimestamp%3D20220825213715", BookState.I, BookSupple.N);
                bookService.registerNewBook("김상현 지음", "정약용 살인사건", "중앙M&B(랜덤하우스중앙)", "2006", "9788959864706", "https://search1.kakaocdn.net/thumb/R120x174.q85/?fname=http%3A%2F%2Ft1.daumcdn.net%2Flbook%2Fimage%2F814870%3Ftimestamp%3D20211204201827", BookState.P, BookSupple.N);
                bookService.registerNewBook("황농문 지음", "몰입", "랜덤하우스코리아", "2007", "9788925514826", "https://search1.kakaocdn.net/thumb/R120x174.q85/?fname=http%3A%2F%2Ft1.daumcdn.net%2Flbook%2Fimage%2F411705%3Ftimestamp%3D20240430112710", BookState.I, BookSupple.N);
                bookService.registerNewBook("홍사중 지음", "나의 논어", "이다미디어", "2006", "9791158741426", "https://search1.kakaocdn.net/thumb/R120x174.q85/?fname=http%3A%2F%2Ft1.daumcdn.net%2Flbook%2Fimage%2F6015264%3Ftimestamp%3D20240723154038", BookState.I, BookSupple.N);
                bookService.registerNewBook("베르나르 베르베르 지음", "파피용", "열린책들", "2007", "9788932907741", "https://search1.kakaocdn.net/thumb/R120x174.q85/?fname=http%3A%2F%2Ft1.daumcdn.net%2Flbook%2Fimage%2F506439%3Ftimestamp%3D20231201015513", BookState.I, BookSupple.N);
                bookService.registerNewBook("박범신", "킬리만자로의눈꽃", "해냄", "1997", "9788998493585", "https://search1.kakaocdn.net/thumb/R120x174.q85/?fname=http%3A%2F%2Ft1.daumcdn.net%2Flbook%2Fimage%2F4402846%3Ftimestamp%3D20190228161200", BookState.P, BookSupple.N);
                bookService.registerNewBook("김진명 저", "한반도", "해냄", "1999", "9788950952518", "https://search1.kakaocdn.net/thumb/R120x174.q85/?fname=http%3A%2F%2Ft1.daumcdn.net%2Flbook%2Fimage%2F625662%3Ftimestamp%3D20220420212720", BookState.P, BookSupple.N);
                bookService.registerNewBook("고찬유 지음", "(거룩한 땅에,) 슈끄리아", "유스북", "2006", "9788991430143", "https://search1.kakaocdn.net/thumb/R120x174.q85/?fname=http%3A%2F%2Ft1.daumcdn.net%2Flbook%2Fimage%2F1349582%3Ftimestamp%3D20220105174508", BookState.P, BookSupple.N);
                bookService.registerNewBook("기욤 뮈소 지음", "구해줘", "밝은세상", "2007", "9788984370753", "https://search1.kakaocdn.net/thumb/R120x174.q85/?fname=http%3A%2F%2Ft1.daumcdn.net%2Flbook%2Fimage%2F1187452%3Ftimestamp%3D20240915112246", BookState.P, BookSupple.N);
                bookService.registerNewBook("로스 엘리자베스 퀴블러   ; 케슬러 데이비드 공저", "인생수업", "이레", "2007", "9788957090817", "https://search1.kakaocdn.net/thumb/R120x174.q85/?fname=http%3A%2F%2Ft1.daumcdn.net%2Flbook%2Fimage%2F751351%3Ftimestamp%3D20240706112942", BookState.I, BookSupple.N);
                bookService.registerNewBook("법정 말씀", "산에는 꽃이 피네", "동쪽나라", "1998", "9788995904992", "https://search1.kakaocdn.net/thumb/R120x174.q85/?fname=http%3A%2F%2Ft1.daumcdn.net%2Flbook%2Fimage%2F1453467%3Ftimestamp%3D20221025122431", BookState.P, BookSupple.N);
                bookService.registerNewBook("코맥 매카시 지음", "로드", "문학동네", "2008", "9788954605908", "https://search1.kakaocdn.net/thumb/R120x174.q85/?fname=http%3A%2F%2Ft1.daumcdn.net%2Flbook%2Fimage%2F690185%3Ftimestamp%3D20240906112452", BookState.P, BookSupple.N);
                bookService.registerNewBook("최일도", "행복하소서", "위즈덤하우스", "2008", "9788960861312", "https://search1.kakaocdn.net/thumb/R120x174.q85/?fname=http%3A%2F%2Ft1.daumcdn.net%2Flbook%2Fimage%2F840862%3Ftimestamp%3D20220917071152", BookState.I, BookSupple.N);
                bookService.registerNewBook("로버트 제임스 윌러 저", "메디슨 카운티의 다리", "시공사", "1994", "9791193783405", "https://search1.kakaocdn.net/thumb/R120x174.q85/?fname=http%3A%2F%2Ft1.daumcdn.net%2Flbook%2Fimage%2F6626858%3Ftimestamp%3D20240621155506", BookState.P, BookSupple.N);
                bookService.registerNewBook("와다 히데키 지음", "남자는 왜? 여자는 왜?", "예문", "2002", "9791165042615", "https://search1.kakaocdn.net/thumb/R120x174.q85/?fname=http%3A%2F%2Ft1.daumcdn.net%2Flbook%2Fimage%2F5910968%3Ftimestamp%3D20240522150401", BookState.P, BookSupple.N);

            }


        }

    }

}

