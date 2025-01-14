package bitcopark.library.service.Book;

import bitcopark.library.controller.search.LikeCondition;
import bitcopark.library.controller.search.LikeStatus;
import bitcopark.library.entity.Book.Book;
import bitcopark.library.entity.Book.BookLike;
import bitcopark.library.entity.member.Member;
import bitcopark.library.repository.Book.BookLikeRepository;
import bitcopark.library.repository.Book.BookRepository;
import bitcopark.library.repository.Member.MemberRepository;
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
        Member findMember = memberRepository.findById(condition.getMemberId()).orElseThrow(() -> new IllegalArgumentException("not valid memberId"));
        Book findBook = bookRepository.findByIsbn(condition.getIsbn()).orElseThrow(() -> new IllegalArgumentException("not valid isbn"));
        return new Result(findMember, findBook);
    }

    private record Result(Member findMember, Book findBook) {
    }



}
