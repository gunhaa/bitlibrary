package bitcopark.library.repository.book;

import bitcopark.library.entity.book.Book;
import bitcopark.library.entity.book.BookBorrow;
import bitcopark.library.entity.member.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookBorrowRepository extends JpaRepository<BookBorrow, Long> {

    List<BookBorrow> findByMember(Member member);

    Boolean existsByBook(Book book);

    List<BookBorrow> findByReturnDateIsNullAndMember(Member member);
}
