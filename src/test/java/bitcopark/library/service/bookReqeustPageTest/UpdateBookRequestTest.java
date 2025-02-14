package bitcopark.library.service.bookReqeustPageTest;

import bitcopark.library.dto.BookRequestCondition;
import bitcopark.library.entity.book.Book;
import bitcopark.library.entity.book.BookState;
import bitcopark.library.entity.book.BookSupple;
import bitcopark.library.entity.member.BookRequest;
import bitcopark.library.entity.member.Member;
import bitcopark.library.dto.LoginMemberDTO;
import bitcopark.library.repository.book.BookRequestRepository;
import bitcopark.library.service.book.BookRequestService;
import bitcopark.library.service.book.BookService;
import bitcopark.library.service.member.MemberService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@SpringBootTest
@Transactional
public class UpdateBookRequestTest {

    @Autowired
    private MemberService memberService;

    @Autowired
    private BookRequestService bookRequestService;

    @Autowired
    private BookRequestRepository bookRequestRepository;

    @Autowired
    private BookService bookService;

    @Test
    @DisplayName("책_요청_업데이트")
    public void 책_요청_업데이트_성공(){
        // given

        // 회원 가입
        String naverEmail = "wh8299@naver.com";
        String naverName = "naver YxUVriKN_IuaBzIWFfCBzzfnVc6SHEkDJtxV9fY8pxQ 황건하";
        String role = "ROLE_USER";
        Member OAuthNaverGunha = memberService.joinOAuth2Member(naverEmail, naverName, role);

        String isbn = "9788971992258";

        // 책 요청 등록
        BookRequestCondition bookRequestCondition = new BookRequestCondition();
        bookRequestCondition.setIsbn(isbn);
        bookRequestCondition.setEmail(naverEmail);
        bookRequestCondition.setBookTitle("책제목");
        bookRequestCondition.setBookAuthor("저자");
        bookRequestCondition.setBookPublisher("출판사");
        bookRequestCondition.setBookPublicationDate(LocalDateTime.now().toLocalDate());
        bookRequestCondition.setOpinion("작성자 의견");
        bookRequestService.registerBookRequest(bookRequestCondition);
        

        // when
        BookRequest bookRequest = bookRequestRepository.findByIsbn(isbn).get();

        String updateOpinion = "업데이트된 작성자 의견";
        String prevIsbn = "9788971992258";
        bookRequestCondition.setOpinion(updateOpinion);
        bookRequestCondition.setPrevIsbn(prevIsbn);
        LoginMemberDTO loginMemberDTO = new LoginMemberDTO(naverEmail, naverName, role);
        ResponseEntity<?> responseEntity = bookRequestService.updateBookRequest(bookRequestCondition, loginMemberDTO);
        BookRequest updateBookRequest = bookRequestRepository.findByIsbn(isbn).get();

        //then
        Assertions.assertThat(bookRequest.getIsbn()).isEqualTo(isbn);
        Assertions.assertThat(updateBookRequest.getOpinion()).isEqualTo(updateOpinion);
        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    @DisplayName("책_요청_실패_이미_존재하는_ISBN")
    public void 책_요청_업데이트실패_이미_존재하는_ISBN(){

        //given
        // 회원 가입
        String naverEmail = "wh8299@naver.com";
        String naverName = "naver YxUVriKN_IuaBzIWFfCBzzfnVc6SHEkDJtxV9fY8pxQ 황건하";
        String role = "ROLE_USER";
        Member OAuthNaverGunha = memberService.joinOAuth2Member(naverEmail, naverName, role);

        String 백범일지_isbn = "9788971992258";

        // 책 등록
        Book 백범일지 = bookService.registerNewBook("김구 저", "백범일지", "돌베개", "2008", 백범일지_isbn, "https://search1.kakaocdn.net/thumb/R120x174.q85/?fname=http%3A%2F%2Ft1.daumcdn.net%2Flbook%2Fimage%2F994701%3Ftimestamp%3D20240727112932", BookState.P, BookSupple.N);
        String isbn = "123123123123";

        // 책 요청 등록
        BookRequestCondition bookRequestCondition = new BookRequestCondition();
        bookRequestCondition.setIsbn(isbn);
        bookRequestCondition.setEmail(naverEmail);
        bookRequestCondition.setBookTitle("책제목");
        bookRequestCondition.setBookAuthor("저자");
        bookRequestCondition.setBookPublisher("출판사");
        bookRequestCondition.setBookPublicationDate(LocalDateTime.now().toLocalDate());
        bookRequestCondition.setOpinion("작성자 의견");
        bookRequestService.registerBookRequest(bookRequestCondition);

        // when
        String updateOpinion = "업데이트된 작성자 의견";
        String prevIsbn = isbn;
        bookRequestCondition.setIsbn(백범일지_isbn);
        bookRequestCondition.setOpinion(updateOpinion);
        bookRequestCondition.setPrevIsbn(prevIsbn);
        LoginMemberDTO loginMemberDTO = new LoginMemberDTO(naverEmail, naverName, role);
        ResponseEntity<?> responseEntity = bookRequestService.updateBookRequest(bookRequestCondition, loginMemberDTO);

        // then
        String bodyMessage = "bookRequest isbn exist";
        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        Assertions.assertThat(responseEntity.getBody()).isEqualTo(bodyMessage);
    }

    // bookRequestCondition을 이용한 POST요청 어뷰징 방지 테스트
    @Test
    @DisplayName("잘못된_유저의_책_요청_업데이트")
    public void 잘못된_유저의_책_요청_업데이트(){
        //given
        // 회원 가입
        String naverEmail = "wh8299@naver.com";
        String naverName = "naver YxUVriKN_IuaBzIWFfCBzzfnVc6SHEkDJtxV9fY8pxQ 황건하";
        String role = "ROLE_USER";
        Member OAuthNaverGunha = memberService.joinOAuth2Member(naverEmail, naverName, role);

        String isbn = "9788971992258";

        // 책 요청 등록
        BookRequestCondition bookRequestCondition = new BookRequestCondition();
        bookRequestCondition.setIsbn(isbn);
        bookRequestCondition.setEmail(naverEmail);
        bookRequestCondition.setBookTitle("책제목");
        bookRequestCondition.setBookAuthor("저자");
        bookRequestCondition.setBookPublisher("출판사");
        bookRequestCondition.setBookPublicationDate(LocalDateTime.now().toLocalDate());
        bookRequestCondition.setOpinion("작성자 의견");
        bookRequestService.registerBookRequest(bookRequestCondition);

        // 책 요청 업데이트
        bookRequestCondition.setEmail("abusing@email.com");
        String updateOpinion = "업데이트된 작성자 의견";
        String prevIsbn = isbn;
        bookRequestCondition.setOpinion(updateOpinion);
        bookRequestCondition.setPrevIsbn(prevIsbn);
        LoginMemberDTO loginMemberDTO = new LoginMemberDTO(naverEmail, naverName, role);

        // when
        ResponseEntity<?> responseEntity = bookRequestService.updateBookRequest(bookRequestCondition, loginMemberDTO);

        // then
        String bodyMessage = "not valid request";
        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        Assertions.assertThat(responseEntity.getBody()).isEqualTo(bodyMessage);
    }

}
