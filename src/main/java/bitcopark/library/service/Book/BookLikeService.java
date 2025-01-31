package bitcopark.library.service.Book;

import bitcopark.library.controller.search.LikeCondition;
import bitcopark.library.controller.search.LikeStatus;
import bitcopark.library.entity.book.Book;
import bitcopark.library.entity.book.BookLike;
import bitcopark.library.entity.member.Member;
import bitcopark.library.repository.book.BookLikeRepository;
import bitcopark.library.repository.book.BookRepository;
import bitcopark.library.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BookLikeService {

    private final BookLikeRepository bookLikeRepository;
    private final MemberRepository memberRepository;
    private final BookRepository bookRepository;

    @Transactional
    public BookLike addBookLike(Member member, Book book){
        BookLike bookLike = BookLike.builder()
                .book(book)
                .member(member)
                .build();
        return bookLikeRepository.save(bookLike);
    }

    @Transactional
    public LikeStatus toggleLike(LikeCondition condition) {
        return condition.getLikeStatus()==LikeStatus.NOT_LIKED ?
                addBookLike(condition) : deleteBookLike(condition);
    }

    private LikeStatus addBookLike(LikeCondition condition) {
        Result result = findMemberAndBook(condition);
        addBookLike(result.findMember(), result.findBook());
        return LikeStatus.LIKED;
    }

    private LikeStatus deleteBookLike(LikeCondition condition) {
        Result result = findMemberAndBook(condition);
        bookLikeRepository.deleteByMemberAndBook(result.findMember(), result.findBook());
        return LikeStatus.NOT_LIKED;
    }

    private Result findMemberAndBook(LikeCondition condition) {
        Member findMember = memberRepository.findByEmail(condition.getEmail()).orElseThrow(() -> new IllegalArgumentException("not valid email"));
        Book findBook = bookRepository.findByIsbn(condition.getIsbn()).orElseThrow(() -> new IllegalArgumentException("not valid isbn"));
        return new Result(findMember, findBook);
    }

    @Transactional
    public void delete(Long id, String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("not found member: " + email));

        BookLike bookLike = bookLikeRepository.findByIdAndMember(id, member)
                .orElseThrow(() -> new IllegalArgumentException("not found bookLike: " + id));

        bookLikeRepository.delete(bookLike);
    }

    private record Result(Member findMember, Book findBook) {
    }



}
