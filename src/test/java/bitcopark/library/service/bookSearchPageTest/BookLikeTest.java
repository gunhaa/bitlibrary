package bitcopark.library.service.bookSearchPageTest;

import bitcopark.library.dto.BookLikeDto;
import bitcopark.library.dto.BookSearchDto;
import bitcopark.library.dto.LikeCondition;
import bitcopark.library.controller.search.LikeStatus;
import bitcopark.library.entity.book.Book;
import bitcopark.library.entity.book.BookLike;
import bitcopark.library.entity.book.BookState;
import bitcopark.library.entity.book.BookSupple;
import bitcopark.library.entity.member.Member;
import bitcopark.library.dto.LoginMemberDTO;
import bitcopark.library.repository.book.*;
import bitcopark.library.repository.member.MemberRepository;
import bitcopark.library.service.book.BookLikeService;
import bitcopark.library.service.book.BookService;
import bitcopark.library.dto.SearchBooklistAndLikelistDTO;
import bitcopark.library.service.member.MemberService;
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

    @Autowired
    private BookLikeRepository bookLikeRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    @DisplayName("좋아요")
    public void 좋아요() {
        //given

        // 회원 가입
        String naverEmail = "wh8299@naver.com";
        String naverName = "naver YxUVriKN_IuaBzIWFfCBzzfnVc6SHEkDJtxV9fY8pxQ 황건하";
        String role = "ROLE_USER";
        Member member1 = memberService.joinOAuth2Member(naverEmail, naverName, role);

        // 책 등록
        Book 백범일지 = bookService.registerNewBook("김구 저", "백범일지", "돌베개", "2008", "9788971992258", "https://search1.kakaocdn.net/thumb/R120x174.q85/?fname=http%3A%2F%2Ft1.daumcdn.net%2Flbook%2Fimage%2F994701%3Ftimestamp%3D20240727112932", BookState.P, BookSupple.N);

        // 좋아요 등록
        bookLikeService.addBookLike(member1, 백범일지);

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
    
    @Test
    @DisplayName("좋아요_토글")
    public void 좋아요_토글(){
        //given

        // 회원 가입
        String naverEmail = "wh8299@naver.com";
        String naverName = "naver YxUVriKN_IuaBzIWFfCBzzfnVc6SHEkDJtxV9fY8pxQ 황건하";
        String role = "ROLE_USER";
        Member member1 = memberService.joinOAuth2Member(naverEmail, naverName, role);

        // 책 등록
        String isbn = "9788971992258";
        Book 백범일지 = bookService.registerNewBook("김구 저", "백범일지", "돌베개", "2008", isbn, "https://search1.kakaocdn.net/thumb/R120x174.q85/?fname=http%3A%2F%2Ft1.daumcdn.net%2Flbook%2Fimage%2F994701%3Ftimestamp%3D20240727112932", BookState.P, BookSupple.N);

        // 좋아요 등록
        bookLikeService.addBookLike(member1, 백범일지);

        // when
        LikeCondition likeCondition = new LikeCondition();
        likeCondition.setIsbn(isbn);
        likeCondition.setEmail(naverEmail);
        likeCondition.setLikeStatus(LikeStatus.LIKED);
        bookLikeService.toggleLike(likeCondition);
        // then
        List<BookLike> bookLikeList = bookLikeRepository.findByMember(member1);
        Assertions.assertThat(bookLikeList.size()).isEqualTo(0);

        //when,then
        LikeCondition likeCondition2 = new LikeCondition();
        likeCondition2.setIsbn(isbn);
        likeCondition2.setEmail(naverEmail);
        likeCondition2.setLikeStatus(LikeStatus.NOT_LIKED);
        bookLikeService.toggleLike(likeCondition2);
        List<BookLike> bookLikeList2 = bookLikeRepository.findByMember(member1);
        Assertions.assertThat(bookLikeList2.size()).isEqualTo(1);
    }

}
