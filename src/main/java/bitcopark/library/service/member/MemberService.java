package bitcopark.library.service.member;

import bitcopark.library.dto.AdminMemberResponse;
import bitcopark.library.entity.member.Member;
import bitcopark.library.dto.LoginMemberDTO;
import bitcopark.library.dto.BookStatusDTO;
import bitcopark.library.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    public Page<AdminMemberResponse> getAllMembersExcludingCurrent(String email, Pageable pageable) {
        Page<Member> memberPage = memberRepository.findAllByEmailNot(email, pageable);

        List<AdminMemberResponse> dtoList = memberPage.getContent().stream().map(AdminMemberResponse::new).toList();

        return new PageImpl<>(dtoList, pageable, memberPage.getTotalElements());
    }

    @Transactional
    public void toggleDeletionStatus(List<String> emails) {
        List<Member> members = memberRepository.findByEmailIn(emails);
        members.forEach(Member::toggleDeletedStatus);
    }
}
