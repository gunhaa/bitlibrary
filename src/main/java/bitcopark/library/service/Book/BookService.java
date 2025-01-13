package bitcopark.library.service.Book;

import bitcopark.library.entity.Book.Book;
import bitcopark.library.entity.Book.BookState;
import bitcopark.library.entity.Book.BookSupple;
import bitcopark.library.exception.BookTitleNotFoundException;
import bitcopark.library.repository.Book.BookRepository;
import bitcopark.library.repository.Book.BookSearchCondition;
import bitcopark.library.repository.Book.BookSearchDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BookService {

    private final BookRepository bookRepository;

    @Transactional
    public BooklistAndLikelistDTO searchBooklistAndLikelist(BookSearchCondition bookSearchCondition){
        List<BookSearchDto> books = bookRepository.findAllBooks(bookSearchCondition);

        // 좋아요 로직 추가 필요
        //bookRepository.findLikeMembers(memberNo);

        System.out.println("books.size() = " + books.size());
        for (BookSearchDto book : books) {
            System.out.println("book = " + book);
        }

        return new BooklistAndLikelistDTO(books, new ArrayList<>());
    }

    @Transactional
    public Book registerNewBook(String author, String title, String publisher, String publicationDate, String isbn, String thumbnail, BookState bookState, BookSupple bookSupple){

        Book book = Book.createBook(author, title, publisher, publicationDate, isbn, thumbnail, bookState, bookSupple);
        bookRepository.save(book);

        return book;
    }

    public Book findBookByTitleOrThrow(String title) {
        return bookRepository.findByTitle(title)
                // client exception 발생시 로그를 남겨, 그 파일을 남기는 방식을 찾을 것
                // 이건 찾아봐야할듯
                .orElseThrow(() -> new BookTitleNotFoundException("책 제목을 찾을 수 없습니다: " + title));
    }

}
