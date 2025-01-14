package bitcopark.library.repository.Book;

import bitcopark.library.entity.Book.BookLike;
import bitcopark.library.entity.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookLikeRepository extends JpaRepository<BookLike, Long> {

    List<BookLike> findByMember(Member member);

    @Query("select new bitcopark.library.repository.Book.BookLikeDto(b.isbn) from BookLike bl join bl.book b where bl.member.id = :memberId")
    List<BookLikeDto> findBookLikeListByMemberId(@Param("memberId") Long MemberId);

}
