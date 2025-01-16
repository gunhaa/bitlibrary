package bitcopark.library.repository.Member;

import bitcopark.library.entity.member.BookRequest;
import bitcopark.library.repository.Book.BookRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRequestRepository extends JpaRepository<BookRequest, Long>, BookRequestRepositoryCustom {

}
