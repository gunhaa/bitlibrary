package bitcopark.library.service.mainPageTest;

import bitcopark.library.entity.member.Address;
import bitcopark.library.entity.member.Member;
import bitcopark.library.repository.member.MemberRepository;
import bitcopark.library.service.Member.MemberService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest
public class MemberTest {

    @Autowired
    private MemberService memberService;
    @Autowired
    private MemberRepository memberRepository;

    @DisplayName("회원_가입")
    @Test
    public void 회원_가입(){
        String naverEmail = "wh8299@naver.com";
        String naverName = "naver YxUVriKN_IuaBzIWFfCBzzfnVc6SHEkDJtxV9fY8pxQ 황건하";
        Member OAuthNaverGunha = memberService.joinOAuth2Member(naverEmail, naverName, "ROLE_ADMIN");

        // when
        boolean isExist = memberRepository.existsByEmail(naverEmail);

        // then
        Assertions.assertThat(isExist).isTrue();
    }


    @Test
    public void 임베디드타입_동등성비교(){
        Address adr1 = new Address("13131", "D동");
        Address adr2 = new Address("13131", "D동");
        Assertions.assertThat(adr1).isEqualTo(adr2);
    }

}
