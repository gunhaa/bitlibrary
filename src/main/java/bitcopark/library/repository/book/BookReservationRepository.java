package bitcopark.library.repository.book;

import bitcopark.library.entity.book.Book;
import bitcopark.library.entity.book.BookReservation;
import bitcopark.library.entity.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookReservationRepository extends JpaRepository<BookReservation, Long> {

    List<BookReservation> findByMember(Member member);

    Long countByMemberAndBook(Member member, Book book);

    Optional<BookReservation> findByIdAndMember(Long id, Member member);
}
