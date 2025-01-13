package bitcopark.library.repository.Book;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@SpringBootTest
@Transactional
public class BookRepositoryCustomImplTest {

    @Autowired
    private BookRepository bookRepository;

    @Test
    public void 동적_쿼리테스트() {

        //given
        //tempGenerateBook에서 책 생성


        //when
        List<BookSearchDto> BookDto = bookRepository.findAllBooks(new BookSearchCondition());

        //then

        System.out.println("BookDto = " + BookDto);
    }

}