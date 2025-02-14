package bitcopark.library.service.book;

import bitcopark.library.dto.ReservationCondition;
import bitcopark.library.dto.BookReservationResponse;
import bitcopark.library.entity.book.Book;
import bitcopark.library.entity.book.BookReservation;
import bitcopark.library.entity.member.Member;
import bitcopark.library.repository.book.BookRepository;
import bitcopark.library.repository.book.BookReservationRepository;
import bitcopark.library.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    @Transactional
    public void delete(Long id, String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("not found member: " + email));

        BookReservation bookReservation = bookReservationRepository.findByIdAndMember(id, member)
                .orElseThrow(() -> new IllegalArgumentException("not found bookReservation: " + id));

        bookReservationRepository.delete(bookReservation);
    }

    public List<BookReservationResponse> getBookReservations(String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("not found member: " + email));

        return bookReservationRepository.findByMember(member)
                .stream().map(BookReservationResponse::new).toList();
    }
}
