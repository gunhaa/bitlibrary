package bitcopark.library.service.bookSearchPageTest;

import bitcopark.library.entity.book.BookState;
import bitcopark.library.entity.book.BookSupple;
import bitcopark.library.repository.book.BookSearchCondition;
import bitcopark.library.dto.BookSearchDto;
import bitcopark.library.repository.book.SearchType;
import bitcopark.library.service.book.BookService;
import bitcopark.library.dto.SearchBooklistAndLikelistDTO;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@SpringBootTest
public class BookSearchTest {

    @Autowired
    private BookService bookService;

    @Test
    @DisplayName("비로그인_책_검색_전체")
    public void 비로그인_책_검색_전체(){
        //given
        for (int i = 0; i < 10; i++) {
            bookService.registerNewBook("김구 저", "백범일지" + i, "돌베개", "2008", "9788971992258" + i, "https://search1.kakaocdn.net/thumb/R120x174.q85/?fname=http%3A%2F%2Ft1.daumcdn.net%2Flbook%2Fimage%2F994701%3Ftimestamp%3D20240727112932", BookState.P, BookSupple.N);
            bookService.registerNewBook("댄 브라운 지음", "디셉션 포인트" + i, "대교베텔스만", "2006", "9788957591574" + i, "https://search1.kakaocdn.net/thumb/R120x174.q85/?fname=http%3A%2F%2Ft1.daumcdn.net%2Flbook%2Fimage%2F762541%3Ftimestamp%3D20220825213715", BookState.I, BookSupple.N);
        }
        //when
        BookSearchCondition bookSearchCondition = new BookSearchCondition();
        bookSearchCondition.setKey(SearchType.ta);
        bookSearchCondition.setQuery("백범");
        SearchBooklistAndLikelistDTO searchBooklistAndLikelistDTO = bookService.searchBooklistAndLikelist(bookSearchCondition);
        System.out.println(searchBooklistAndLikelistDTO);

        //then
        List<BookSearchDto> bookList = searchBooklistAndLikelistDTO.getBookList();
        Assertions.assertThat(bookList.size()).isEqualTo(10);
    }

    @Test
    @DisplayName("비로그인_책_검색_작가")
    public void 비로그인_책_검색_작가(){
        //given
        for (int i = 0; i < 10; i++) {
            bookService.registerNewBook("김구 저", "백범일지" + i, "돌베개", "2008", "9788971992258" + i, "https://search1.kakaocdn.net/thumb/R120x174.q85/?fname=http%3A%2F%2Ft1.daumcdn.net%2Flbook%2Fimage%2F994701%3Ftimestamp%3D20240727112932", BookState.P, BookSupple.N);
            bookService.registerNewBook("댄 브라운 지음", "디셉션 포인트" + i, "대교베텔스만", "2006", "9788957591574" + i, "https://search1.kakaocdn.net/thumb/R120x174.q85/?fname=http%3A%2F%2Ft1.daumcdn.net%2Flbook%2Fimage%2F762541%3Ftimestamp%3D20220825213715", BookState.I, BookSupple.N);
        }
        //when
        BookSearchCondition bookSearchCondition = new BookSearchCondition();
        bookSearchCondition.setKey(SearchType.a);
        bookSearchCondition.setQuery("김구");
        SearchBooklistAndLikelistDTO searchBooklistAndLikelistDTO = bookService.searchBooklistAndLikelist(bookSearchCondition);
        System.out.println(searchBooklistAndLikelistDTO);

        //then
        List<BookSearchDto> bookList = searchBooklistAndLikelistDTO.getBookList();
        Assertions.assertThat(bookList.size()).isEqualTo(10);
    }

    @Test
    @DisplayName("비로그인_책_검색_제목")
    public void 비로그인_책_검색_제목(){
        //given
        for (int i = 0; i < 10; i++) {
            bookService.registerNewBook("김구 저", "백범일지" + i, "돌베개", "2008", "9788971992258" + i, "https://search1.kakaocdn.net/thumb/R120x174.q85/?fname=http%3A%2F%2Ft1.daumcdn.net%2Flbook%2Fimage%2F994701%3Ftimestamp%3D20240727112932", BookState.P, BookSupple.N);
            bookService.registerNewBook("댄 브라운 지음", "디셉션 포인트" + i, "대교베텔스만", "2006", "9788957591574" + i, "https://search1.kakaocdn.net/thumb/R120x174.q85/?fname=http%3A%2F%2Ft1.daumcdn.net%2Flbook%2Fimage%2F762541%3Ftimestamp%3D20220825213715", BookState.I, BookSupple.N);
        }
        //when
        BookSearchCondition bookSearchCondition = new BookSearchCondition();
        bookSearchCondition.setKey(SearchType.t);
        bookSearchCondition.setQuery("디셉션");
        SearchBooklistAndLikelistDTO searchBooklistAndLikelistDTO = bookService.searchBooklistAndLikelist(bookSearchCondition);

        //then
        List<BookSearchDto> bookList = searchBooklistAndLikelistDTO.getBookList();
        Assertions.assertThat(bookList.size()).isEqualTo(10);
    }

}
