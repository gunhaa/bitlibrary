package bitcopark.library.service.bookReqeustPageTest;

import bitcopark.library.controller.book.BookRequestCondition;
import bitcopark.library.controller.book.BookRequestResponseDto;
import bitcopark.library.entity.member.Member;
import bitcopark.library.repository.member.MemberRepository;
import bitcopark.library.service.Book.BookRequestService;
import bitcopark.library.service.Member.MemberService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@SpringBootTest
@Transactional
public class BookRequestPageTest {

    @Autowired
    private BookRequestService bookRequestService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberRepository memberRepository;


    @Test
    @DisplayName("책_요청_등록")
    public void 책_요청_등록(){
        // given

        // 회원 가입
        String naverEmail = "wh8299@naver.com";
        String naverName = "naver YxUVriKN_IuaBzIWFfCBzzfnVc6SHEkDJtxV9fY8pxQ 황건하";
        Member OAuthNaverGunha = memberService.joinOAuth2Member(naverEmail, naverName, "ROLE_ADMIN");

        // when
        boolean isExist = memberRepository.existsByEmail(naverEmail);

        // 책 요청 등록
        BookRequestCondition bookRequestCondition = new BookRequestCondition();
        bookRequestCondition.setIsbn("123123123123123");
        bookRequestCondition.setEmail("wh8299@naver.com");
        bookRequestCondition.setBookTitle("책제목");
        bookRequestCondition.setBookAuthor("저자");
        bookRequestCondition.setBookPublisher("출판사");
        bookRequestCondition.setBookPublicationDate(LocalDateTime.now().toLocalDate());
        bookRequestCondition.setOpinion("작성자 의견");
        // when
        BookRequestResponseDto bookRequestResponseDto = bookRequestService.registerBookRequest(bookRequestCondition);
        System.out.println(bookRequestResponseDto);

        // then
        Assertions.assertThat(bookRequestResponseDto.isSuccess()).isTrue();
        Assertions.assertThat(bookRequestResponseDto.getMessage()).isEqualTo("Book Request Success");
        
    }

}
