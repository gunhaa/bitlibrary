package bitcopark.library.service.Book;

import bitcopark.library.controller.book.BookSearchDetailCondition;
import bitcopark.library.entity.book.Book;
import bitcopark.library.entity.book.BookState;
import bitcopark.library.entity.book.BookSupple;
import bitcopark.library.entity.member.Member;
import bitcopark.library.exception.BookTitleNotFoundException;
import bitcopark.library.repository.book.*;
import bitcopark.library.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BookService {

    private final BookRepository bookRepository;
    private final BookLikeRepository bookLikeRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public SearchBooklistAndLikelistDTO searchBooklistAndLikelist(BookSearchCondition bookSearchCondition){

        List<BookSearchDto> books = bookRepository.findSearchConditionBooks(bookSearchCondition);
        List<BookLikeDto> bookLikeListByMemberId = getBookLikeDto(bookSearchCondition.getMemberId());

        return new SearchBooklistAndLikelistDTO(books, bookLikeListByMemberId);
    }

    @Transactional
    public SearchBooklistAndLikelistDTO searchDetailBooklistAndLikelist(BookSearchDetailCondition bookSearchDetailCondition) {

        List<BookSearchDto> books = bookRepository.findSearchDetailConditionBooks(bookSearchDetailCondition);
        List<BookLikeDto> bookLikeListByMemberId = getBookLikeDto(bookSearchDetailCondition.getMemberId());

        return new SearchBooklistAndLikelistDTO(books, bookLikeListByMemberId);
    }

    private List<BookLikeDto> getBookLikeDto(Long bookSearchDetailCondition) {
        Member findMember = memberRepository.findById(bookSearchDetailCondition).orElseThrow(() -> new IllegalArgumentException("memberId를 찾을 수 없습니다."));
        return bookLikeRepository.findBookLikeListByMemberId(findMember.getId());
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
