package bitcopark.library.service.Book;

import bitcopark.library.entity.Book.Book;
import bitcopark.library.entity.Book.BookLike;
import bitcopark.library.entity.Book.BookState;
import bitcopark.library.entity.Book.BookSupple;
import bitcopark.library.entity.member.Member;
import bitcopark.library.exception.BookTitleNotFoundException;
import bitcopark.library.repository.Book.*;
import bitcopark.library.repository.Member.MemberRepository;
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
    private final BookLikeRepository bookLikeRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public BooklistAndLikelistDTO searchBooklistAndLikelist(BookSearchCondition bookSearchCondition){
        List<BookSearchDto> books = bookRepository.findAllBooks(bookSearchCondition);

        Member findMember = memberRepository.findById(bookSearchCondition.getMemberId()).orElseThrow(() -> new IllegalArgumentException("memberId를 찾을 수 없습니다."));
        List<BookLikeDto> bookLikeListByMemberId = bookLikeRepository.findBookLikeListByMemberId(findMember.getId());

        for (BookLikeDto bookLikeDto : bookLikeListByMemberId) {
            System.out.println("bookLikeDto.getIsbn() = " + bookLikeDto.getIsbn());
        }

        return new BooklistAndLikelistDTO(books, bookLikeListByMemberId);
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
