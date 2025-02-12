package bitcopark.library.repository.member;

import bitcopark.library.entity.member.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long>, MemberRepositoryCustom {

//    Optional<Member> findByEmailAndPassword(String email, String password);

    boolean existsByEmail(String email);

    Optional<Member> findByName(String name);

    boolean existsByName(String name);

    Optional<Member> findByEmail(String email);

    @Query("SELECT m.id FROM Member m WHERE m.email = :email")
    Long findMemberIdByName(@Param("email") String email);

    Page<Member> findAllByEmailNot(String email, Pageable pageable);
}
