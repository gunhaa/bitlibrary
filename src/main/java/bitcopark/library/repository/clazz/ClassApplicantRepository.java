package bitcopark.library.repository.clazz;

import bitcopark.library.entity.clazz.ClassApplicant;
import bitcopark.library.entity.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ClassApplicantRepository extends JpaRepository<ClassApplicant,Long> {

    List<ClassApplicant> findByMember(Member member);

    Optional<ClassApplicant> findByIdAndMember(Long id, Member member);
}
