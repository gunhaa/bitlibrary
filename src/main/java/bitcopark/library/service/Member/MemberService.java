package bitcopark.library.service.Member;

import bitcopark.library.entity.member.Member;
import bitcopark.library.jwt.LoginMemberDTO;
import bitcopark.library.repository.book.BookStatusDTO;
import bitcopark.library.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public Member joinOAuth2Member(String email, String name, String authority){
        Member oAuth2Member = Member.createOAuth2Member(email, name, authority);
        memberRepository.save(oAuth2Member);
        return oAuth2Member;
    }


    public BookStatusDTO getBookStatus(LoginMemberDTO loginMember) {
        Member findMember = memberRepository.findByEmail(loginMember.getEmail()).orElseThrow(() -> new IllegalArgumentException("not valid email"));
        BookStatusDTO bookStatus = memberRepository.findBookStatus(findMember);
        return bookStatus;
    }

    @Transactional
    public void softDeleteMember(String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("not found email: " + email));

        member.softDelete();
    }
}
