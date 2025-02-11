package bitcopark.library.service.bookReqeustPageTest;

import bitcopark.library.controller.book.BookRequestCondition;
import bitcopark.library.entity.member.BookRequest;
import bitcopark.library.entity.member.Member;
import bitcopark.library.jwt.LoginMemberDTO;
import bitcopark.library.repository.book.BookRequestRepository;
import bitcopark.library.service.Book.BookRequestService;
import bitcopark.library.service.Member.MemberService;
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

    @Test
    @DisplayName("책_요청_수정")
    public void 책_요청_수정_성공(){
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
        bookRequestCondition.setEmail("wh8299@naver.com");
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

}
