package bitcopark.library.repository.book;

import bitcopark.library.entity.member.BookRequest;
import bitcopark.library.entity.member.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface BookRequestRepository extends JpaRepository<BookRequest, Long>, BookRequestRepositoryCustom {

    @Query("select bq from BookRequest bq join fetch bq.member m where m.email = :email")
    Optional<BookRequest> findByEmail(String email);

    Optional<BookRequest> findByIsbn(String isbn);

    @Transactional
    void deleteByIsbn(String isbn);

    Page<BookRequest> findByMember(Member member, Pageable pageable);
}
