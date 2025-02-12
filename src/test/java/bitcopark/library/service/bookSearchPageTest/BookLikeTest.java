package bitcopark.library.service.bookSearchPageTest;

import bitcopark.library.entity.book.Book;
import bitcopark.library.entity.book.BookState;
import bitcopark.library.entity.book.BookSupple;
import bitcopark.library.entity.member.Member;
import bitcopark.library.jwt.LoginMemberDTO;
import bitcopark.library.repository.book.BookLikeDto;
import bitcopark.library.repository.book.BookSearchCondition;
import bitcopark.library.repository.book.BookSearchDto;
import bitcopark.library.repository.book.SearchType;
import bitcopark.library.service.Book.BookLikeService;
import bitcopark.library.service.Book.BookService;
import bitcopark.library.service.Book.SearchBooklistAndLikelistDTO;
import bitcopark.library.service.Member.MemberService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
@Transactional
public class BookLikeTest {

    @Autowired
    private BookService bookService;

    @Autowired
    private BookLikeService bookLikeService;

    @Autowired
    private MemberService memberService;

    @Test
    @DisplayName("좋아요")
    public void 좋아요() {
        //given

        // 회원 가입
        String naverEmail = "wh8299@naver.com";
        String naverName = "naver YxUVriKN_IuaBzIWFfCBzzfnVc6SHEkDJtxV9fY8pxQ 황건하";
        String role = "ROLE_USER";
        Member member1 = memberService.joinOAuth2Member(naverEmail, naverName, role);
        Member member2 = memberService.joinOAuth2Member("test1@email.com", "naver YxUVriKN_IuaBzIWFfCBzzfnVc6SHEkDJtxV9fY8pxQ 황건하1", "ROLE_USER");
        Member member3 = memberService.joinOAuth2Member("test2@email.com", "naver YxUVriKN_IuaBzIWFfCBzzfnVc6SHEkDJtxV9fY8pxQ 황건하2", "ROLE_USER");
        Member member4 = memberService.joinOAuth2Member("test3@email.com", "naver YxUVriKN_IuaBzIWFfCBzzfnVc6SHEkDJtxV9fY8pxQ 황건하3", "ROLE_USER");

        // 책 등록
        Book 백범일지 = bookService.registerNewBook("김구 저", "백범일지", "돌베개", "2008", "9788971992258", "https://search1.kakaocdn.net/thumb/R120x174.q85/?fname=http%3A%2F%2Ft1.daumcdn.net%2Flbook%2Fimage%2F994701%3Ftimestamp%3D20240727112932", BookState.P, BookSupple.N);

        // 좋아요 등록
        bookLikeService.addBookLike(member1, 백범일지);
        bookLikeService.addBookLike(member2, 백범일지);
        bookLikeService.addBookLike(member3, 백범일지);
        bookLikeService.addBookLike(member4, 백범일지);

        //when
        BookSearchCondition bookSearchCondition = new BookSearchCondition();
        bookSearchCondition.setKey(SearchType.t);
        bookSearchCondition.setQuery("백범");
        LoginMemberDTO loginMemberDTO = new LoginMemberDTO(naverEmail, naverName, role);
        SearchBooklistAndLikelistDTO searchBooklistAndLikelistDTO = bookService.searchBooklistAndLikelist(bookSearchCondition, loginMemberDTO);

        //then
        List<BookSearchDto> bookList = searchBooklistAndLikelistDTO.getBookList();
        List<BookLikeDto> likeList = searchBooklistAndLikelistDTO.getLikeList();
        Assertions.assertThat(bookList.get(0).getIsbn()).isEqualTo(likeList.get(0).getIsbn());

    }

}
