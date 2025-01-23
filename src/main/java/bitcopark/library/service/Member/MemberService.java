package bitcopark.library.service.Member;

import bitcopark.library.dto.LoginRequestDTO;
import bitcopark.library.dto.LoginResponseDTO;
import bitcopark.library.entity.member.Address;
import bitcopark.library.entity.member.Member;
import bitcopark.library.entity.member.MemberGender;
import bitcopark.library.exception.EmailDuplicateException;
import bitcopark.library.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public Member joinMember(String email, String password, String name, String phoneNumber, MemberGender gender, LocalDate birthDate, Address address){

        validateDuplicateMember(email);

        Member member = Member.createMember(email, name, phoneNumber, gender, birthDate, address);

        memberRepository.save(member);

        return member;
    }

    @Transactional
    public Member joinAdmin(String email, String password, String name, String phoneNumber, MemberGender gender, LocalDate birthDate, Address address){

        validateDuplicateMember(email);

        Member member = Member.createAdmin(email, name, phoneNumber, gender, birthDate, address);

        memberRepository.save(member);

        return member;
    }

    private void validateDuplicateMember(String email) {
        if (memberRepository.existsByEmail(email)) {
            throw new EmailDuplicateException("중복된 이메일입니다.");
        }
    }

    public Optional<LoginResponseDTO> login(LoginRequestDTO request) {
        return memberRepository.findByEmailAndPassword(request.getEmail(), request.getPassword())
                .map(LoginResponseDTO::new);
    }
}
