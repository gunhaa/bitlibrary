package bitcopark.library.service.Book;

import bitcopark.library.controller.search.ReservationCondition;
import bitcopark.library.entity.book.Book;
import bitcopark.library.entity.book.BookReservation;
import bitcopark.library.entity.member.Member;
import bitcopark.library.repository.Book.BookRepository;
import bitcopark.library.repository.Book.BookReservationRepository;
import bitcopark.library.repository.Member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BookReservationService {

    private final BookReservationRepository bookReservationRepository;
    private final MemberRepository memberRepository;
    private final BookRepository bookRepository;

    @Transactional
    public BookReservation registerBookReservation(Member member , Book book){
        BookReservation bookReservation = BookReservation.builder()
                .member(member)
                .book(book)
                .build();
        return bookReservationRepository.save(bookReservation);
    }

    @Transactional
    public ReservationStatus registerBookReservationWithIsbnAndMemberId(ReservationCondition condition) {
        
        Member findMember = memberRepository.findById(condition.getMemberId()).orElseThrow(() -> new IllegalArgumentException("잘못된 회원 번호 입니다"));
        Book findBook = bookRepository.findByIsbn(condition.getIsbn()).orElseThrow(()->new IllegalArgumentException("잘못된 isbn 입니다."));
        Long count = bookReservationRepository.countByMemberAndBook(findMember, findBook);

        if(count>0) {
            return ReservationStatus.ALREADY_RESERVED;
        }

        registerBookReservation(findMember, findBook);
        return ReservationStatus.SUCCESS;
    }
}
