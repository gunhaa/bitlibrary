package bitcopark.library.repository.book;

import bitcopark.library.entity.member.BookRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRequestRepository extends JpaRepository<BookRequest, Long>, BookRequestRepositoryCustom {

}
