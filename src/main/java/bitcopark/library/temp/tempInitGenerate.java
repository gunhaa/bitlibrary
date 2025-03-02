package bitcopark.library.temp;

import bitcopark.library.dto.BoardRequestDTO;
import bitcopark.library.entity.board.Category;
import bitcopark.library.entity.book.Book;
import bitcopark.library.entity.book.BookState;
import bitcopark.library.entity.book.BookSupple;
import bitcopark.library.entity.member.BookRequestApprove;
import bitcopark.library.entity.member.Member;
import bitcopark.library.jwt.JwtUtil;
import bitcopark.library.dto.LoginMemberDTO;
import bitcopark.library.repository.board.CategoryRepository;
import bitcopark.library.service.board.BoardService;
import bitcopark.library.service.board.CategoryService;
import bitcopark.library.service.book.BookBorrowService;
import bitcopark.library.service.book.BookLikeService;
import bitcopark.library.service.book.BookReservationService;
import bitcopark.library.service.book.BookService;
import bitcopark.library.service.book.BookRequestService;
import bitcopark.library.service.member.MemberService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Profile("local")
@Component
@RequiredArgsConstructor
public class tempInitGenerate {

    private final initService initService;

    @PostConstruct
    public void init() {
        initService.init();
    }

    @Component
    @RequiredArgsConstructor
    @Profile("local")
    private static class initService {

        private final MemberService memberService;

        private final CategoryService categoryService;
        private final CategoryRepository categoryRepository;

        private final BookService bookService;

        private final BookBorrowService bookBorrowService;

        private final BookLikeService bookLikeService;

        private final BookReservationService bookReservationService;

        private final BoardService boardService;

        private final BookRequestService bookRequestService;

        private final JwtUtil jwtUtil;

        @Transactional
        public void init() {

            Category 공지사항=null;
            Category 문의사항=null;
            Category 책_후기_나눠요=null;
            if (categoryRepository.selectCategoryCount() != 49) {
                // 기존 데이터 삭제
                categoryRepository.deleteAll();
                // 레벨 1 카테고리 생성
                Category 도서관_안내 = categoryService.createNewCategory("도서관 안내", "intro");
                Category 자료_검색 = categoryService.createNewCategory("자료 검색", "search");
                Category 참여_마당 = categoryService.createNewCategory("참여 마당", "community");
                Category 이용자_마당 = categoryService.createNewCategory("이용자 마당", "user");
                Category 맛있는_도서관 = categoryService.createNewCategory("맛있는 도서관", "delicious-library");
                Category 내_서재 = categoryService.createNewCategory("내 서재", "study");

                // 레벨 2 카테고리 생성
                Category 도서관_소개 = categoryService.createNewCategoryWithParentCategory("도서관 소개", 도서관_안내, "introduction");
                Category 이용안내 = categoryService.createNewCategoryWithParentCategory("이용안내", 도서관_안내, "guide");
                categoryService.createNewCategoryWithParentCategory("시설안내", 도서관_안내, "facility-guide");

                Category 통합자료_검색 = categoryService.createNewCategoryWithParentCategory("통합자료 검색", 자료_검색, "books");
                Category 희망_도서_신청 = categoryService.createNewCategoryWithParentCategory("희망 도서 신청", 자료_검색, "book-req");

                categoryService.createNewCategoryWithParentCategory("교육문화 프로그램", 참여_마당, "edu-culture-program");
                categoryService.createNewCategoryWithParentCategory("열람실", 참여_마당, "reading-room");
                categoryService.createNewCategoryWithParentCategory("세미나실", 참여_마당, "seminar-room");

                공지사항 = categoryService.createNewCategoryWithParentCategory("공지사항", 이용자_마당, "notice");
                문의사항 = categoryService.createNewCategoryWithParentCategory("문의사항", 이용자_마당, "inquiries");
                categoryService.createNewCategoryWithParentCategory("자주 묻는 질문", 이용자_마당, "faq");
                책_후기_나눠요 = categoryService.createNewCategoryWithParentCategory("책 후기 나눠요", 이용자_마당, "book-reviews");

                categoryService.createNewCategoryWithParentCategory("구내식당", 맛있는_도서관, "cafeteria");
                categoryService.createNewCategoryWithParentCategory("카페", 맛있는_도서관, "cafe");

                Category 나의_도서관 = categoryService.createNewCategoryWithParentCategory("나의 도서관", 내_서재, "my-library");
                Category 회원정보 = categoryService.createNewCategoryWithParentCategory("회원정보", 내_서재, "member-info");

                // 레벨 3 카테고리 생성
                categoryService.createNewCategoryWithParentCategory("인사말", 도서관_소개, "greetings");
                categoryService.createNewCategoryWithParentCategory("연혁", 도서관_소개, "history");
                categoryService.createNewCategoryWithParentCategory("조직도", 도서관_소개, "organization-chart");
                categoryService.createNewCategoryWithParentCategory("도서관 오시는 길", 도서관_소개, "directions");
                categoryService.createNewCategoryWithParentCategory("deletedCategory", 도서관_소개, "nearby-libraries");

                categoryService.createNewCategoryWithParentCategory("이용시간", 이용안내, "operating-hours");
                categoryService.createNewCategoryWithParentCategory("deletedCategory", 이용안내, "library-calendar");

                categoryService.createNewCategoryWithParentCategory("간략 검색", 통합자료_검색, "quick-search");
                categoryService.createNewCategoryWithParentCategory("상세 검색", 통합자료_검색, "detailed-search");

                Category 도서현황 = categoryService.createNewCategoryWithParentCategory("도서현황", 나의_도서관, "book-status");
                Category 예약_및_신청 = categoryService.createNewCategoryWithParentCategory("예약 및 신청", 나의_도서관, "reservation-and-requests");
                categoryService.createNewCategoryWithParentCategory("즐겨찾기", 나의_도서관, "favorites");

                categoryService.createNewCategoryWithParentCategory("내 글 관리", 회원정보, "my-posts");
                categoryService.createNewCategoryWithParentCategory("회원탈퇴", 회원정보, "membership-cancellation");

                // 레벨 4 카테고리 생성
                categoryService.createNewCategoryWithParentCategory("대출중인 도서", 도서현황, "books-on-loan");
                categoryService.createNewCategoryWithParentCategory("이전 대출내역", 도서현황, "previous-loan-history");
                categoryService.createNewCategoryWithParentCategory("예약내역", 도서현황, "reservation-history");
                categoryService.createNewCategoryWithParentCategory("신청내역", 도서현황, "application-history");

                categoryService.createNewCategoryWithParentCategory("좌석 예약현황", 예약_및_신청, "seat-reservation-status");
                categoryService.createNewCategoryWithParentCategory("공간 예약현황", 예약_및_신청, "space-reservation-status");
                categoryService.createNewCategoryWithParentCategory("클래스 신청현황", 예약_및_신청, "class-application-status");
            }


            Book 명품_인생을_살아라 = bookService.registerNewBook("박은몽 글", "명품 인생을 살아라", "대교베텔스만", "2008", "9788957592472", "https://search1.kakaocdn.net/thumb/R120x174.q85/?fname=http%3A%2F%2Ft1.daumcdn.net%2Flbook%2Fimage%2F762800%3Ftimestamp%3D20220904155651", BookState.P, BookSupple.N);
            Book 백범일지 = bookService.registerNewBook("김구 저", "백범일지", "돌베개", "2008", "9788971992258", "https://search1.kakaocdn.net/thumb/R120x174.q85/?fname=http%3A%2F%2Ft1.daumcdn.net%2Flbook%2Fimage%2F994701%3Ftimestamp%3D20240727112932", BookState.P, BookSupple.N);

            for (int i = 0; i < 32; i++) {
                bookService.registerNewBook("김구 저", "백범일지" + i, "돌베개", "2008", "9788971992258" + i, "https://search1.kakaocdn.net/thumb/R120x174.q85/?fname=http%3A%2F%2Ft1.daumcdn.net%2Flbook%2Fimage%2F994701%3Ftimestamp%3D20240727112932", BookState.P, BookSupple.N);
            }

            Book 디셉션_포인트 = bookService.registerNewBook("댄 브라운 지음", "디셉션 포인트", "대교베텔스만", "2006", "9788957591574", "https://search1.kakaocdn.net/thumb/R120x174.q85/?fname=http%3A%2F%2Ft1.daumcdn.net%2Flbook%2Fimage%2F762541%3Ftimestamp%3D20220825213715", BookState.I, BookSupple.N);
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
            // 테스트 멤버 생성
            Member member1 = memberService.joinOAuth2Member("test1@email.com", "naver YxUVriKN_IuaBzIWFfCBzzfnVc6SHEkDJtxV9fY8pxQ 황건하1", "ROLE_USER");
            Member member2 = memberService.joinOAuth2Member("test2@email.com", "naver YxUVriKN_IuaBzIWFfCBzzfnVc6SHEkDJtxV9fY8pxQ 황건하2", "ROLE_USER");
            Member member3 = memberService.joinOAuth2Member("test3@email.com", "naver YxUVriKN_IuaBzIWFfCBzzfnVc6SHEkDJtxV9fY8pxQ 황건하3", "ROLE_USER");

            String naverEmail = "wh8299@naver.com";
            String naverName = "naver YxUVriKN_IuaBzIWFfCBzzfnVc6SHEkDJtxV9fY8pxQ 황건하";
            Member OAuthNaverGunha = memberService.joinOAuth2Member(naverEmail, naverName, "ROLE_ADMIN");

            String googleEmail = "wh8299@gmail.com";
            String googleName = "google 108061151955961388292 황건하";
            Member OAuthGoogleGunha = memberService.joinOAuth2Member(googleEmail, googleName, "ROLE_USER");

            // 공지사항 생성
            for (int i = 0; i < 50 ; i++) {
                LoginMemberDTO loginMemberDTO = new LoginMemberDTO(naverEmail, naverName, "ROLE_ADMIN");
                BoardRequestDTO boardRequestDTO = new BoardRequestDTO();
                boardRequestDTO.setTitle("공지사항 제목"+i);
                boardRequestDTO.setContent("공지사항 내용"+i);
                boardService.writePost(loginMemberDTO, boardRequestDTO, 공지사항);
            }

            for (int i = 0; i < 50 ; i++) {
                LoginMemberDTO loginMemberDTO = new LoginMemberDTO(naverEmail, naverName, "ROLE_ADMIN");
                BoardRequestDTO boardRequestDTO = new BoardRequestDTO();
                boardRequestDTO.setTitle("문의사항 제목"+i);
                boardRequestDTO.setContent("문의사항 내용"+i);
                boardService.writePost(loginMemberDTO, boardRequestDTO, 문의사항);
            }

            for (int i = 0; i < 50 ; i++) {
                LoginMemberDTO loginMemberDTO = new LoginMemberDTO(naverEmail, naverName, "ROLE_ADMIN");
                BoardRequestDTO boardRequestDTO = new BoardRequestDTO();
                boardRequestDTO.setTitle("책 후기 나눠요 제목"+i);
                boardRequestDTO.setContent("책 후기 나눠요 내용"+i);
                boardService.writePost(loginMemberDTO, boardRequestDTO, 책_후기_나눠요);
            }


            // 책 좋아요
            bookLikeService.addBookLike(member1, 명품_인생을_살아라);
            bookLikeService.addBookLike(member1, 백범일지);
            bookLikeService.addBookLike(member2, 백범일지);
            bookLikeService.addBookLike(member1, 디셉션_포인트);
            bookLikeService.addBookLike(member2, 디셉션_포인트);
            bookLikeService.addBookLike(member3, 디셉션_포인트);

            // 책 대여
            bookBorrowService.registerBookRental(member1, 백범일지);
            bookBorrowService.registerBookRental(member1, 디셉션_포인트);

            bookBorrowService.registerBookRental(OAuthNaverGunha, 디셉션_포인트);
            bookBorrowService.registerBookRental(OAuthNaverGunha, 백범일지);

            //책 예약
            bookReservationService.registerBookReservation(OAuthNaverGunha, 백범일지);
            bookReservationService.registerBookReservation(member1, 백범일지);
            bookReservationService.registerBookReservation(member2, 백범일지);
            bookReservationService.registerBookReservation(member1, 디셉션_포인트);
            bookReservationService.registerBookReservation(member2, 디셉션_포인트);
            bookReservationService.registerBookReservation(member3, 디셉션_포인트);

            //책 요청
            for (int i = 0; i < 200; i++) {
                bookRequestService.createBookRequest(1L, "1234567890" + i, "bookTitle" + i, "출판사" + i, "작가" + i, BookRequestApprove.W, LocalDate.now(), "의견" + i);
            }


        }

    }

}

