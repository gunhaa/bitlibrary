package bitcopark.library.service.Class;

import bitcopark.library.entity.clazz.ClassApplicant;
import bitcopark.library.entity.board.Board;
import bitcopark.library.entity.member.Member;
import bitcopark.library.repository.clazz.ClassApplicantRepository;
import bitcopark.library.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ClassApplicantService {

    private final ClassApplicantRepository classApplicantRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public ClassApplicant registerClassApplicant(Board board, Member member){
        ClassApplicant classApplicant = ClassApplicant.builder()
                .board(board)
                .member(member)
                .build();
        return classApplicantRepository.save(classApplicant);
    }

    @Transactional
    public void delete(Long id, String email) {
        Member member = memberRepository.findByEmail(email).
                orElseThrow(() -> new IllegalArgumentException("not found member: " + id));

        ClassApplicant classApplicant = classApplicantRepository.findByIdAndMember(id, member)
                .orElseThrow(() -> new IllegalArgumentException("not found classApplicant: " + id));

        classApplicantRepository.delete(classApplicant);
    }
}
