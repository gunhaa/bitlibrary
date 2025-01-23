package bitcopark.library.repository.book;

import bitcopark.library.entity.book.Book;
import bitcopark.library.entity.book.BookLike;
import bitcopark.library.entity.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookLikeRepository extends JpaRepository<BookLike, Long> {

    List<BookLike> findByMember(Member member);

    @Modifying
    void deleteByMemberAndBook(Member member, Book book);

    @Query("select new bitcopark.library.repository.book.BookLikeDto(b.isbn) from BookLike bl join bl.book b where bl.member.id = :memberId")
    List<BookLikeDto> findBookLikeListByMemberId(@Param("memberId") Long MemberId);

}
