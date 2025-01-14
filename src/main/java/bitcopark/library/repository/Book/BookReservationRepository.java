package bitcopark.library.repository.Book;

import bitcopark.library.entity.Book.Book;
import bitcopark.library.entity.Book.BookReservation;
import bitcopark.library.entity.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookReservationRepository extends JpaRepository<BookReservation, Long> {

    List<BookReservation> findByMember(Member member);

    Long countByMemberAndBook(Member member, Book book);
}
